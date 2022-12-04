package si.fri.rso.notifications.services.beans;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.rso.notifications.lib.Notification;
import si.fri.rso.notifications.models.converters.NotificationConverter;
import si.fri.rso.notifications.models.entities.NotificationEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;


@RequestScoped
public class NotificationBean {

    private Logger log = Logger.getLogger(NotificationBean.class.getName());

    @Inject
    private EntityManager em;

    public List<Notification> getNotification() {

        TypedQuery<NotificationEntity> query = em.createNamedQuery(
                "NotificationEntity.getAll", NotificationEntity.class);

        List<NotificationEntity> resultList = query.getResultList();

        return resultList.stream().map(NotificationConverter::toDto).collect(Collectors.toList());

    }

    public List<Notification> getNotificationFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, NotificationEntity.class, queryParameters).stream()
                .map(NotificationConverter::toDto).collect(Collectors.toList());
    }

    public Notification getNotification(Integer id) {

        NotificationEntity NotificationEntity = em.find(NotificationEntity.class, id);

        if (NotificationEntity == null) {
            throw new NotFoundException();
        }

        Notification notification = NotificationConverter.toDto(NotificationEntity);

        return notification;
    }

    public Notification createNotification(Notification notification) {

        NotificationEntity NotificationEntity = NotificationConverter.toEntity(notification);

        try {
            beginTx();
            em.persist(NotificationEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (NotificationEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return NotificationConverter.toDto(NotificationEntity);
    }

    public Notification putNotification(Integer id, Notification notification) {

        NotificationEntity c = em.find(NotificationEntity.class, id);

        if (c == null) {
            return null;
        }

        NotificationEntity updatedNotificationEntity = NotificationConverter.toEntity(notification);

        try {
            beginTx();
            updatedNotificationEntity.setId(c.getId());
            updatedNotificationEntity = em.merge(updatedNotificationEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return NotificationConverter.toDto(updatedNotificationEntity);
    }

    public boolean deleteNotification(Integer id) {

        NotificationEntity notification = em.find(NotificationEntity.class, id);

        if (notification != null) {
            try {
                beginTx();
                em.remove(notification);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
