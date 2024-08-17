package FitMate.FitMateBackend.chanhaleWorking.repository;

import FitMate.FitMateBackend.user.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    @PersistenceContext
    private final EntityManager em;

    public void save(User user){
        if (user.getId() == null) {
            em.persist(user);
        }else{
            em.merge(user);
        }
    }
    public Optional<User> findByLoginEmail(String loginEmail) {
        return em.createQuery("select u from User u where u.loginEmail = :loginEmail ", User.class)
                .setParameter("loginEmail", loginEmail)
                .getResultList().stream().findFirst();
    }

    public Boolean CheckDuplicatedLoginEmail(String loginEmail){
        return em.createQuery("select u from User u where u.loginEmail = :loginEmail ", User.class)
                .setParameter("loginEmail", loginEmail)
                .getResultList().size() > 0;
    }

    public void deleteUser(Long id) {
        User user = em.find(User.class, id);
        if (!(user == null)) {
            em.remove(user);
        }
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }
}
