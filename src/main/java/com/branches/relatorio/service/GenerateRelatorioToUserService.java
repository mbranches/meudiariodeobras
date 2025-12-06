package com.branches.relatorio.service;

import com.branches.arquivo.domain.ArquivoEntity;
import com.branches.atividade.domain.AtividadeDeRelatorioEntity;
import com.branches.comentarios.model.ComentarioDeRelatorioEntity;
import com.branches.equipamento.domain.EquipamentoDeRelatorioEntity;
import com.branches.external.aws.S3UploadFile;
import com.branches.maodeobra.domain.MaoDeObraDeRelatorioEntity;
import com.branches.material.domain.MaterialDeRelatorioEntity;
import com.branches.obra.domain.ObraEntity;
import com.branches.ocorrencia.domain.OcorrenciaDeRelatorioEntity;
import com.branches.relatorio.domain.AssinaturaDeRelatorioEntity;
import com.branches.relatorio.repository.projections.RelatorioDetailsProjection;
import com.branches.tenant.domain.TenantEntity;
import com.branches.usertenant.domain.PermissionsItensDeRelatorio;
import com.branches.usertenant.domain.UserTenantEntity;
import com.branches.utils.FileContentType;
import com.branches.utils.HtmlToPdfConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class GenerateRelatorioToUserService {
    private final GenerateRelatorioHtmlService generateRelatorioHtmlService;
    private final HtmlToPdfConverter htmlToPdfConverter;
    private final S3UploadFile s3UploadFile;

    public String execute(
            RelatorioDetailsProjection relatorioDetails,
            TenantEntity tenant,
            ObraEntity obra,
            UserTenantEntity userTenant,
            List<OcorrenciaDeRelatorioEntity> ocorrenciasDoRelatorio,
            List<AtividadeDeRelatorioEntity> atividadesDoRelatorio,
            List<EquipamentoDeRelatorioEntity> equipamentosDoRelatorio,
            List<MaoDeObraDeRelatorioEntity> maoDeObraDoRelatorio,
            List<ComentarioDeRelatorioEntity> comentariosDoRelatorio,
            List<MaterialDeRelatorioEntity> materiaisDoRelatorio,
            List<ArquivoEntity> fotosDoRelatorio,
            List<ArquivoEntity> videosDoRelatorio,
            List<AssinaturaDeRelatorioEntity> assinaturas
    ) {
        PermissionsItensDeRelatorio userPermissionsItensDeRelatorio = userTenant.getAuthorities().getItensDeRelatorio();

        String html = generateRelatorioHtmlService.execute(
                relatorioDetails,
                ocorrenciasDoRelatorio,
                atividadesDoRelatorio,
                equipamentosDoRelatorio,
                maoDeObraDoRelatorio,
                comentariosDoRelatorio,
                materiaisDoRelatorio,
                fotosDoRelatorio,
                videosDoRelatorio,
                assinaturas,
                userPermissionsItensDeRelatorio.getCondicaoDoClima(),
                userPermissionsItensDeRelatorio.getHorarioDeTrabalho()
        );

        byte[] pdfBytes = htmlToPdfConverter.execute(html);

        String fileName = "relatorio_" + userTenant.getUser().getIdExterno() + ".pdf";
        String path = "tenants/%s/obras/%s/relatorios/%s".formatted(tenant.getIdExterno(), obra.getIdExterno(), relatorioDetails.getIdExterno());

        //o usuário já possui a url do arquivo na entidade ArquivoDeRelatorioDeUsuarioEntity, isso foi feito na criação do relatório, ou no momento da permissao do user para a obra
        return s3UploadFile.execute(fileName, path, pdfBytes, FileContentType.PDF);
    }
}
