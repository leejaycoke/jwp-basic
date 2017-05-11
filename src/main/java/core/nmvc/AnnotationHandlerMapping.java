package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class AnnotationHandlerMapping {
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    private static final Logger logger = LoggerFactory.getLogger(AnnotationHandlerMapping.class);

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        Reflections reflections = new Reflections("core.nmvc");
        Set<Class<?>> classes =  reflections.getTypesAnnotatedWith(Controller.class);
        List<Method> methods = Arrays.stream(classes.stream().getClass().getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(RequestMapping.class))
                .collect(Collectors.toList());

        for (Method method : methods) {
            RequestMapping mapping = method.getDeclaredAnnotation(RequestMapping.class);
            HandlerKey handlerKey = new HandlerKey(mapping.value(), mapping.method());
            handlerExecutions.put(handlerKey, );
        }

        methods.stream()
                .filter(method -> method.getDeclaredAnnotation(RequestMapping.class));
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }

}
