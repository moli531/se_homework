public class producer
{
    public static abstractfactory getFactory(String type)
            throws IllegalAccessException, InstantiationException, ClassNotFoundException
    {
        Class cl = Class.forName(type);
        return (abstractfactory)cl.newInstance();
    }
}