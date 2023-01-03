package si.fri.rso.notifications.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.notifications.services.beans.NotificationBean;
import si.fri.rso.samples.imagecatalog.lib.ImageMetadata;
import si.fri.rso.notifications.lib.Notification;
import si.fri.rso.samples.imagecatalog.services.beans.ImageMetadataBean;
import com.kumuluz.ee.cors.annotations.CrossOrigin;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;


import com.kumuluz.ee.logs.cdi.Log;

@ApplicationScoped
@Path("/notifications")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@CrossOrigin(allowOrigin = "*")
@Log
public class NotificationResource {

    private Logger log = Logger.getLogger(si.fri.rso.notifications.api.v1.resources.NotificationResource.class.getName());

    @Inject
    private si.fri.rso.notifications.services.beans.NotificationBean NotificationBean;

    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all notifications.", summary = "Get all metadata")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of notifications",
                    content = @Content(schema = @Schema(implementation = Notification.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getNotification() {

        List<Notification> notification = NotificationBean.getNotificationFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(notification).build();
    }


    @Operation(description = "Get metadata for an image.", summary = "Get metadata for an image")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Image metadata",
                    content = @Content(
                            schema = @Schema(implementation = Notification.class))
            )})
    @GET
    @Path("/{notificationId}")
    public Response getNotification(@Parameter(description = "Notification ID.", required = true)
                                     @PathParam("notificationId") Integer notificationId) {

        Notification notification = NotificationBean.getNotification(notificationId);

        if (notification == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(notification).build();
    }

    @Operation(description = "Add notification.", summary = "Add metadata")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Notification successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createNotification(@RequestBody(
            description = "DTO object with image metadata.",
            required = true, content = @Content(
            schema = @Schema(implementation = Notification.class))) Notification notification) {

        if ((notification.getTitle() == null || notification.getDescription() == null )) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            notification = NotificationBean.createNotification(notification);
        }

        return Response.status(Response.Status.CONFLICT).entity(notification).build();

    }


    @Operation(description = "Update notification.", summary = "Update metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Notification successfully updated."
            )
    })
    @PUT
    @Path("{notificationId}")
    public Response putNotification(@Parameter(description = "Notification ID.", required = true)
                                     @PathParam("notificationId") Integer notificationId,
                                     @RequestBody(
                                             description = "DTO object with image metadata.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Notification.class)))
                                     Notification notification){

        notification = NotificationBean.putNotification(notificationId, notification);

        if (notification == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete metadata for an image.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{notificationId}")
    public Response deleteNotification(@Parameter(description = "Notification ID.", required = true)
                                        @PathParam("notificationId") Integer notificationId){

        boolean deleted = NotificationBean.deleteNotification(notificationId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }





}
