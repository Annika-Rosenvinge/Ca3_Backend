package facades;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
import dtos.UserDTO;
import entities.Role;
import entities.User;
import errorhandling.NotFoundException;
import security.errorhandling.AuthenticationException;
import utils.EMF_Creator;

public class UserFacade{

    private static UserFacade instance;
    private static EntityManagerFactory emf;

    private UserFacade() {}

    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    //dette er kun for alm bruger
    public UserDTO create(UserDTO userDTO){
        EntityManager em = getEntityManager();
        User user = new User(userDTO.getUserName(), userDTO.getPassword(), userDTO.getEmail(), userDTO.getRoleList());

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public UserDTO getById(Integer id) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, id);
        return new UserDTO(user);
    }

    public Long getUserCount(){
        EntityManager em = getEntityManager();
        try{
            Long userCount = (Long) em.createQuery("SELECT COUNT(u) FROM User u").getSingleResult();
            return userCount;
        }finally{
            em.close();
        }
    }


    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        UserFacade userFacade = getUserFacade(emf);
        userFacade.getAll().forEach(dto->System.out.println(dto));
    }

    public User getVerifiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
            else {
                System.out.println("logging in");
            }
        } finally {
            em.close();
        }
        return user;
    }


    public User delete(int id) throws NotFoundException {
        EntityManager em = getEntityManager();
        User user = em.find(User.class, id);
        if (user == null)
            throw new NotFoundException("Could not remove Profile with id: "+id);
        em.getTransaction().begin();
        em.remove(user);
        em.getTransaction().commit();
        return user;
    }

    public List<UserDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> user = query.getResultList();
        return UserDTO.getDtos(user);
    }
    
    public List<UserDTO> getAllUsers(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> userList = query.getResultList();
        return UserDTO.getDtos(userList);
    }

}
