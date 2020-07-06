package dropwizard.controller;


import dropwizard.ReadJson;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import dropwizard.Convert;


@Path("/plot")
@Produces(MediaType.APPLICATION_JSON)
public class PlotRestController {

    public PlotRestController() {
    }

    @GET
    @Path("/{fileName}")
    public Response getData(@PathParam("fileName") String fileName) {
        return Response.ok(Convert.convert(ReadJson.read(fileName))).build();
    }


}
