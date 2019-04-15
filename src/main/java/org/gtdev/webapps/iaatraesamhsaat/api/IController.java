package org.gtdev.webapps.iaatraesamhsaat.api;

import org.pf4j.ExtensionPoint;

import java.util.List;

public interface IController extends ExtensionPoint {
    List<?> reactiveRoutes();
    List<Object> mvcControllers();
}
