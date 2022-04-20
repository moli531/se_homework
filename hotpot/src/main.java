import java.util.Scanner;

public class main {
    public static void main(String[] args) throws IllegalAccessException,
            InstantiationException, ClassNotFoundException
    {
        System.out.println("请输入选择的火锅（数字）：" +
                "1.重庆：鸳鸯火锅 2.重庆：羊肉火锅 3.重庆：毛肚火锅" +
                "4.成都：鸳鸯火锅 5.成都：羊肉火锅 6.成都：毛肚火锅");
        Scanner scan = new Scanner(System.in);
        int a = scan.nextInt();
        abstractfactory abstractFactory = producer.getFactory("CQfactory");
        abstractfactory abstractFactory1 = producer.getFactory("CDfactory");
        switch(a){
            case 1:
                hotpot CQhotpot = abstractFactory.getHotpot("YY1");
                CQhotpot.order();
                break;

            case 2:
                hotpot CQhotpot1 = abstractFactory.getHotpot("YR1");
                CQhotpot1.order();
                break;
            case 3:
                hotpot CQhotpot2 = abstractFactory.getHotpot("MD1");
                CQhotpot2.order();
                break;

            case 4:
                hotpot CDhotpot = abstractFactory1.getHotpot("YY2");
                CDhotpot.order();
                break;

            case 5:
                hotpot CDhotpot1 = abstractFactory1.getHotpot("YR2");
                CDhotpot1.order();
                break;

            case 6:
                hotpot CDhotpot2 = abstractFactory1.getHotpot("MD2");
                CDhotpot2.order();
                break;
        }
    }

}
