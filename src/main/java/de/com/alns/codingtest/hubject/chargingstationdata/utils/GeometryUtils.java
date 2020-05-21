package de.com.alns.codingtest.hubject.chargingstationdata.utils;

import com.vividsolutions.jts.geom.*;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.util.GeometricShapeFactory;
import de.com.alns.codingtest.hubject.chargingstationdata.services.dtos.PointLocationDTO;

public class GeometryUtils {

    private static GeometryFactory jtsGeoFactory;
    private static GeometricShapeFactory jtsGeoShapeFactory;

    private static GeometryFactory getJtsGeoFactory() {
        if (jtsGeoFactory == null) {
            jtsGeoFactory = new GeometryFactory(new PrecisionModel(), 4326);
        }
        return jtsGeoFactory;
    }

    private static GeometricShapeFactory getJtsGeoShapeFactory() {
        if (jtsGeoShapeFactory == null) {
            jtsGeoShapeFactory = new GeometricShapeFactory();
        }
        return jtsGeoShapeFactory;
    }

    public static PointLocationDTO convertJtsGeometryToPointLocationDTO(Point pJtsGeometryPoint) {
        PointLocationDTO objResult = null;

        if (pJtsGeometryPoint != null) {
            objResult = new PointLocationDTO(pJtsGeometryPoint.getCoordinate().getOrdinate(0),
                                             pJtsGeometryPoint.getCoordinate().getOrdinate(1));
        }

        return objResult;
    }

    public static Point convertPointLocationDTOToJtsGeometry(PointLocationDTO pPointLocationDTO) {
        Point objResult = null;
        Coordinate oneCoordenate;

        if (pPointLocationDTO != null) {
            /*
            oneCoordenate = new Coordinate(pPointLocationDTO.getLatitude(), pPointLocationDTO.getLongitude());
            objResult = getJtsGeoFactory().createPoint(oneCoordenate);
            objResult.setSRID(4326);
            */
            WKTReader wktCarnica = new WKTReader();
            try {
                objResult = (Point) wktCarnica.read("POINT (" + pPointLocationDTO.getLatitude() + " " + pPointLocationDTO.getLongitude() + ")");
                objResult.setSRID(4326);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        return objResult;
    }

    public static Polygon buildCircleGeometryPerimiter(PointLocationDTO pCircleCentralPoint, Double pRadius) {
        Polygon objResult = null;
        double diameterInMeters;

        if (pCircleCentralPoint != null && pRadius != null) {
            // Based on: https://stackoverflow.com/a/52857147
            diameterInMeters = (pRadius * 1000) * 2; // Transform in Km and multiply by 2 to get the Diameter

            getJtsGeoShapeFactory().setNumPoints(64); // adjustable
            getJtsGeoShapeFactory().setCentre(new Coordinate(pCircleCentralPoint.getLatitude(),
                                                             pCircleCentralPoint.getLongitude()));
            // Length in meters of 1° of latitude = always 111.32 km
            getJtsGeoShapeFactory().setWidth(diameterInMeters / 111320d);
            // Length in meters of 1° of longitude = 40075 km * cos( latitude ) / 360
            getJtsGeoShapeFactory().setHeight(diameterInMeters /
                                              (40075000 * Math.cos(Math.toRadians(pCircleCentralPoint.getLatitude())) / 360));

            objResult = getJtsGeoShapeFactory().createEllipse();
            objResult.setSRID(4326);
        }

        return objResult;
    }
}
