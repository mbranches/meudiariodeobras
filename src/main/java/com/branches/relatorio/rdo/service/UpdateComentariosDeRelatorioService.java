package com.branches.relatorio.rdo.service;

import com.branches.relatorio.rdo.domain.ComentarioDeRelatorioEntity;
import com.branches.relatorio.rdo.domain.RelatorioEntity;
import com.branches.relatorio.rdo.dto.request.ComentarioDeRelatorioRequest;
import com.branches.relatorio.rdo.repository.ComentarioDeRelatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UpdateComentariosDeRelatorioService {
    private final ComentarioDeRelatorioRepository comentarioDeRelatorioRepository;
    private final GetComentarioListByRelatorioIdAndIdInService getComentarioListByRelatorioIdAndIdInService;

    public void execute(List<ComentarioDeRelatorioRequest> requestList, RelatorioEntity relatorio, Long tenantId) {
        if (requestList == null || requestList.isEmpty()) {
            comentarioDeRelatorioRepository.removeAllByRelatorioId(relatorio.getId());

            return;
        }

        List<ComentarioDeRelatorioEntity> updatedComentarios = updateExistingComentarios(requestList,  relatorio, tenantId);
        List<ComentarioDeRelatorioEntity> newComentarios = createNewComentarios(requestList, relatorio, tenantId);

        List<ComentarioDeRelatorioEntity> comentariosToSave = new ArrayList<>(updatedComentarios);
        comentariosToSave.addAll(newComentarios);

        removeAllNotMentioned(updatedComentarios, relatorio.getId());

        comentarioDeRelatorioRepository.saveAll(comentariosToSave);
    }

    private void removeAllNotMentioned(List<ComentarioDeRelatorioEntity> updatedComentarios, Long id) {
        List<Long> idsToKeep = updatedComentarios.stream()
                .map(ComentarioDeRelatorioEntity::getId)
                .toList();

        comentarioDeRelatorioRepository.removeAllByIdNotInAndRelatorioId(idsToKeep, id);
    }

    private List<ComentarioDeRelatorioEntity> createNewComentarios(List<ComentarioDeRelatorioRequest> requestList, RelatorioEntity relatorio, Long tenantId) {
        return requestList.stream()
                .filter(r -> r.id() == null)
                .map(request -> {
                    ComentarioDeRelatorioEntity entity = new ComentarioDeRelatorioEntity();

                    entity.setRelatorio(relatorio);
                    entity.setCamposPersonalizados(new ArrayList<>());
                    setNewFields(entity, request, tenantId);

                    return entity;
                })
                .toList();
    }

    private List<ComentarioDeRelatorioEntity> updateExistingComentarios(List<ComentarioDeRelatorioRequest> requestList, RelatorioEntity relatorio, Long tenantId) {
        List<Long> ids = requestList.stream()
                .map(ComentarioDeRelatorioRequest::id)
                .filter(Objects::nonNull)
                .toList();

        Map<Long, ComentarioDeRelatorioRequest> requestMap = requestList.stream()
                .filter(r -> r.id() != null)
                .collect(Collectors.toMap(ComentarioDeRelatorioRequest::id, Function.identity()));

        var existingComentarios = getComentarioListByRelatorioIdAndIdInService.execute(relatorio.getId(), ids);
        existingComentarios.forEach(comentario -> {
            var request = requestMap.get(comentario.getId());

            setNewFields(comentario, request, tenantId);
        });

        return existingComentarios;
    }

    private void setNewFields(ComentarioDeRelatorioEntity entity, ComentarioDeRelatorioRequest request, Long tenantId) {
        entity.setDescricao(request.descricao());
        entity.getCamposPersonalizados().clear();
        entity.getCamposPersonalizados().addAll(
                request.camposPersonalizados().stream().map(c -> c.toEntity(tenantId)).toList()
        );
    }
}
