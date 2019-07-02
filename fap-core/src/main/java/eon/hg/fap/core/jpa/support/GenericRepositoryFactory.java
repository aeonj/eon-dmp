package eon.hg.fap.core.jpa.support;

import org.springframework.data.jpa.provider.PersistenceProvider;
import org.springframework.data.repository.core.EntityInformation;
import org.springframework.data.repository.core.RepositoryInformation;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import javax.persistence.EntityManager;

public class GenericRepositoryFactory extends RepositoryFactorySupport {
    private final EntityManager entityManager;

    public GenericRepositoryFactory(EntityManager entityManager) {
        this.entityManager = entityManager;
        PersistenceProvider.fromEntityManager(entityManager);
    }

    /**
     * Returns the {@link EntityInformation} for the given domain class.
     *
     * @param domainClass
     * @return
     */
    @Override
    public <T, ID> EntityInformation<T, ID> getEntityInformation(Class<T> domainClass) {
        return null;
    }

    /**
     * Create a repository instance as backing for the query proxy.
     *
     * @param information
     * @return
     */
    @Override
    protected Object getTargetRepository(RepositoryInformation information) {
        Object repository = getTargetRepositoryViaReflection(information, entityManager);
        return repository;
    }

    /**
     * Returns the base class backing the actual repository instance. Make sure
     * {@link #getTargetRepository(RepositoryInformation)} returns an instance of this class.
     *
     * @param metadata
     * @return
     */
    @Override
    protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {
        return SimpleGenericRepository.class;
    }
}
