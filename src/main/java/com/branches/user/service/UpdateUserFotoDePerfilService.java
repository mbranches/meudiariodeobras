package com.branches.user.service;

import com.branches.external.aws.S3DeleteFile;
import com.branches.external.aws.S3UploadFile;
import com.branches.user.domain.UserEntity;
import com.branches.user.dto.request.UpdateUserFotoDePerfilRequest;
import com.branches.user.repository.UserRepository;
import com.branches.utils.CompressImage;
import com.branches.utils.FileContentType;
import com.branches.utils.ImageOutPutFormat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class UpdateUserFotoDePerfilService {
    private final CompressImage compressImage;
    private final S3UploadFile s3UploadFile;
    private final UserRepository userRepository;
    private final S3DeleteFile s3DeleteFile;

    public void execute(UserEntity user, UpdateUserFotoDePerfilRequest request) {
        String base64Image = request.base64Image();

        byte[] compressedImage = compressImage.execute(base64Image, 500, 500, 0.7, ImageOutPutFormat.JPEG);

        String path = "users/%s".formatted(user.getIdExterno());
        String fileName = "foto-perfil-%s.jpeg".formatted(LocalDateTime.now());
        String urlFotoPerfil = s3UploadFile.execute(fileName, path, compressedImage, FileContentType.JPEG);

        String oldFotoUrl = user.getFotoUrl();
        user.setFotoUrl(urlFotoPerfil);

        userRepository.save(user);

        if (oldFotoUrl == null || oldFotoUrl.isBlank()) {
            return;
        }

        try {
            log.info("Iniciando deleção da foto antiga do S3: {}", oldFotoUrl);

            s3DeleteFile.execute(oldFotoUrl);

            log.info("Foto antiga deletada com sucesso do S3: {}", oldFotoUrl);
        } catch (Exception e) {
            log.error("Erro ao deletar a foto antiga do S3: {}. Erro: {}", oldFotoUrl, e.getMessage());
        }
    }
}
