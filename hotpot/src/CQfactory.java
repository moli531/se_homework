
public class CQfactory extends abstractfactory {
    @Override
    public hotpot getHotpot(String type) throws ClassNotFoundException,IllegalAccessException,InstantiationException
    {
        Class cl = Class.forName(type);
        return (CQ)cl.newInstance();

    }
}
