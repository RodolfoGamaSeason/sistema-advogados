package dev.rodolfo.sistema_advogados.service;

import dev.rodolfo.sistema_advogados.entity.Advogado;
import dev.rodolfo.sistema_advogados.entity.Processo;
import dev.rodolfo.sistema_advogados.repository.AdvogadoRepository;
import dev.rodolfo.sistema_advogados.repository.ProcessoRepositoty;
import dev.rodolfo.sistema_advogados.viewModel.ProcessoViewModel;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepositoty processoRepositoty;

    @Autowired
    private AdvogadoRepository advogadoRepository;

    private Advogado buscaAdvogadoAutenticado() throws UsernameNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Advogado advogado = advogadoRepository.findByUsername(username);

        if (advogado ==  null) {
            throw new UsernameNotFoundException("Advogado não encontrado");
        }

        return advogado;
    }

    public void adicionarProcesso(ProcessoViewModel viewModel) {
        Advogado advogado = buscaAdvogadoAutenticado();

        Processo processo = new Processo();
        processo.setCodProcesso(viewModel.getCodProcesso());
        processo.setAreaDireito(viewModel.getAreaDireito());
        processo.setJuizResponsavel(viewModel.getJuizResponsavel());
        processo.setDescricao(viewModel.getDescricao());

        processo.getAdvogados().add(advogado);
        advogado.getProcessos().add(processo);

        processoRepositoty.save(processo);
    }

    public List<Processo> listarProcessosPorIdAdvogado() {
        Advogado advogado = buscaAdvogadoAutenticado();

        return processoRepositoty.findByAdvogadosId(advogado.getId());
    }

    public List<Processo> listarTodosOsProcessos() {
        return processoRepositoty.findAll();
    }

    public void deletarProcesso(Long id) throws AccessDeniedException {
        Advogado advogado = buscaAdvogadoAutenticado();

        Processo processo = processoRepositoty.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Processo não encontrado"));


        if (!advogado.getProcessos().contains(processo)) {
            throw new AccessDeniedException("Você não tem permissão de deletar esse processo");
        }

        advogado.getProcessos().remove(processo);
        advogadoRepository.save(advogado);

        processoRepositoty.delete(processo);
    }
}