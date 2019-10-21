package ex0502.pyrmont.startup;

import ex0502.pyrmont.core.SimpleContext;
import ex0502.pyrmont.core.SimpleContextMapper;
import ex0502.pyrmont.core.SimpleLoader;
import ex0502.pyrmont.core.SimpleWrapper;
import ex0502.pyrmont.valves.ClientIPLoggerValve;
import ex0502.pyrmont.valves.HeaderLoggerValve;
import org.apache.catalina.*;
import org.apache.catalina.connector.http.HttpConnector;

public final class Bootstrap2 {

    public static void main(String[] args) {

        HttpConnector connector = new HttpConnector();

        // 这里定义了两个SimpleWrapper，每个SimpleWrapper对应一个处理类
        // 对于pipeline中的Valve，都是使用默认的SimpleWrapperValve
        Wrapper wrapper1 = new SimpleWrapper();
        wrapper1.setName("Primitive");
        wrapper1.setServletClass("PrimitiveServlet");

        Wrapper wrapper2 = new SimpleWrapper();
        wrapper2.setName("Modern");
        wrapper2.setServletClass("ModernServlet");

        // 这里在add SimpleWrapper的时候，将每个SimpleWrapper的parent给设置，设置parent的用处是会使用parent的Loader
        Context context = new SimpleContext();
        context.addChild(wrapper1);
        context.addChild(wrapper2);

        // 这里加了两个Valve？
        Valve valve1 = new HeaderLoggerValve();
        Valve valve2 = new ClientIPLoggerValve();

        // HeaderLogger -> ClientIPLogger -> ???
        ((Pipeline) context).addValve(valve1);
        ((Pipeline) context).addValve(valve2);

        // 这里是什么？过滤http？为什么要加一个http的SimpleContextMapper
        Mapper mapper = new SimpleContextMapper();
        mapper.setProtocol("http");
        context.addMapper(mapper);

        // 这里再添加一个SimpleLoader
        // 这个SimpleLoader会在每个SimpleWrapper的实例中去加载自定义实现Servlet协议的class
        Loader loader = new SimpleLoader();
        context.setLoader(loader);

        // 这里有些熟悉了，为什么String path和处理的类能有映射关系？
        // pattern是String path，name是class的名字

        // context.addServletMapping(pattern, name);
        context.addServletMapping("/Primitive", "Primitive");
        context.addServletMapping("/Modern", "Modern");

        connector.setContainer(context);

        try {
            connector.initialize();
            connector.start();

            // make the application wait until we press a key.
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}