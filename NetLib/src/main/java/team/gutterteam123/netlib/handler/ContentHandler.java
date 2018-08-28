package team.gutterteam123.netlib.handler;

import com.google.common.reflect.ClassPath;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import team.gutterteam123.netlib.linked.MasterLinked;
import team.gutterteam123.netlib.packetbase.json.JsonPacket;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

public class ContentHandler extends RootAuthHandler<JsonPacket> {

    private static HashMap<Class<? extends JsonPacket>, Method> handlers = new HashMap<>();
    private static Logger logger = LoggerFactory.getLogger(ContentHandler.class);

    static {
        try {
            for (ClassPath.ClassInfo clazz : ClassPath.from(Thread.currentThread().getContextClassLoader()).getTopLevelClassesRecursive("team.gutterteam123.netlib.handler.contenthandlers")) {
                for (Method method : clazz.load().getMethods()) {
                    if (method.isAnnotationPresent(PacketTarget.class))  {
                        if (method.getParameterCount() == 1) {
                            throw new HandlerLoadException("Invalid Parameter length");
                        }
                        Class<?> parameter = method.getParameterTypes()[0];
                        if (JsonPacket.class.isAssignableFrom(parameter)) {
                            handlers.put((Class<? extends JsonPacket>) parameter, method);
                            logger.info("Registered " + clazz.getSimpleName() + "#" + method.getName() + "(" + parameter.getName() + ")");
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JsonPacket jsonPacket) throws Exception {
        Method method = handlers.get(jsonPacket.getClass());
        method.invoke(null, jsonPacket);
    }

    @Override
    protected Set<String> getRoots() {
        return MasterLinked.getInstance().getRoots().get();
    }
}
