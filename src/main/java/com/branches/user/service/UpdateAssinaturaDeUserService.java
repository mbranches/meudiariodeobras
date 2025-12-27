package com.branches.user.service;

import com.branches.external.aws.S3DeleteFile;
import com.branches.external.aws.S3UploadFile;
import com.branches.user.domain.UserAssinaturaEntity;
import com.branches.user.domain.UserEntity;
import com.branches.user.dto.request.UpdateAssinaturaDeUserRequest;
import com.branches.user.repository.UserAssinaturaRepository;
import com.branches.utils.CompressImage;
import com.branches.utils.FileContentType;
import com.branches.utils.ImageOutPutFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Transactional
@RequiredArgsConstructor
@Service
public class UpdateAssinaturaDeUserService {
    private final CompressImage compressImage;
    private final S3UploadFile s3UploadFile;
    private final UserAssinaturaRepository userAssinaturaRepository;
    private final S3DeleteFile s3DeleteFile;

    public void execute(UpdateAssinaturaDeUserRequest request, UserEntity user) {
        byte[] assinaturaBytes = compressImage.execute(request.base64Assinatura(), 400, 400, 0.8, ImageOutPutFormat.PNG);

        String path = "users/%s".formatted(user.getIdExterno());
        String fileName = "assinatura-%s.png".formatted(LocalDateTime.now());

        String assinaturaUrl = s3UploadFile.execute(fileName, path, assinaturaBytes, FileContentType.PNG);

        UserAssinaturaEntity userAssinatura = getUserAssinaturaEntity(user);
        String oldAssinaturaUrl = userAssinatura.getAssinaturaUrl();

        userAssinatura.setAssinaturaUrl(assinaturaUrl);

        userAssinaturaRepository.save(userAssinatura);

        if (oldAssinaturaUrl == null || oldAssinaturaUrl.isEmpty()) return;

        try {
            log.info("Deletando assinatura antiga do S3: {}", oldAssinaturaUrl);

            s3DeleteFile.execute(oldAssinaturaUrl);

            log.info("Assinatura antiga deletada com sucesso do S3: {}", oldAssinaturaUrl);
        } catch (Exception e) {
            log.info("Não foi possível deletar a assinatura antiga do S3: {}. Erro: {}", oldAssinaturaUrl, e.getMessage());
        }
    }

    private UserAssinaturaEntity getUserAssinaturaEntity(UserEntity user) {
        return userAssinaturaRepository.findByUserId(user.getId())
                .orElseGet(() -> UserAssinaturaEntity.builder().user(user).build());
    }
}
