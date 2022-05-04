package facades;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

//import errorhandling.RenameMeNotFoundException;
import dtos.UserDTO;
import entities.User;
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

    public UserDTO create(UserDTO userDTO){
        User user = new User(userDTO.getUserName(), userDTO.getEmail(), userDTO.getPassword(), userDTO.getRoleList());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new UserDTO(user);
    }

    public UserDTO getById(Integer id) { //throws RenameMeNotFoundException {
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
    
    public List<UserDTO> getAll(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> user = query.getResultList();
        return UserDTO.getDtos(user);
    }
    
    public static void main(String[] args) {
        emf = EMF_Creator.createEntityManagerFactory();
        UserFacade userFacade = getUserFacade(emf);
        userFacade.getAll().forEach(dto->System.out.println(dto));
    }

    //login metode?
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

    public List<UserDTO> getAllUsers(){
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> userList = query.getResultList();
        return UserDTO.getDtos(userList);
    }

}
