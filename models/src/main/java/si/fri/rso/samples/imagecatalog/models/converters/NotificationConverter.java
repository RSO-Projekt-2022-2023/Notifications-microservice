package si.fri.rso.samples.imagecatalog.models.converters;

import si.fri.rso.notifications.lib.Notification;
import si.fri.rso.samples.imagecatalog.models.entities.NotificationEntity;

public class NotificationConverter {

    public static Notification toDto(NotificationEntity entity) {

        Notification dto = new Notification();
        dto.setNotificationId(entity.getId());
        dto.setCreated(entity.getCreated());
        dto.setDescription(entity.getDescription());
        dto.setTitle(entity.getTitle());


        return dto;

    }

    public static NotificationEntity toEntity(Notification dto) {

        NotificationEntity entity = new NotificationEntity();
        entity.setCreated(dto.getCreated());
        entity.setDescription(dto.getDescription());
        entity.setTitle(dto.getTitle());


        return entity;

    }

}
