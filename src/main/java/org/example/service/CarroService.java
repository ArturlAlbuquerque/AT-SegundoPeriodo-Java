package org.example.service;
import org.example.dto.CarroDTOInput;
import org.example.dto.CarroDTOOutput;
import org.example.model.Carro;
import org.modelmapper.ModelMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.stream.Collectors;

public class CarroService {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Carros");
    private ModelMapper modelMapper = new ModelMapper();

    public List<CarroDTOOutput> listar() {
        EntityManager em = emf.createEntityManager();
        List<Carro> carros = em.createQuery("from Carro", Carro.class).getResultList();
        em.close();
        return carros.stream()
                .map(carro -> modelMapper.map(carro, CarroDTOOutput.class))
                .collect(Collectors.toList());
    }

    public void inserir(CarroDTOInput carroDTOInput) {
        Carro carro = modelMapper.map(carroDTOInput, Carro.class);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(carro);
        em.getTransaction().commit();
        em.close();
    }

    public void alterar(CarroDTOInput carroDTOInput) {
        Carro carro = modelMapper.map(carroDTOInput, Carro.class);
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.merge(carro);
        em.getTransaction().commit();
        em.close();
    }

    public CarroDTOOutput buscar(int id) {
        EntityManager em = emf.createEntityManager();
        Carro carro = em.find(Carro.class, id);
        em.close();
        if (carro != null) {
            return modelMapper.map(carro, CarroDTOOutput.class);
        }
        return null;
    }

    public void excluir(int id) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        Carro carro = em.find(Carro.class, id);
        if (carro != null) {
            em.remove(carro);
        }
        em.getTransaction().commit();
        em.close();
    }
}
