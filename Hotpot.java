public class Hotpot{
	public static void main(String[] args) {
		//火锅工厂生产
		Factory md= new MDFactory();  //毛肚火锅
		Hotpot a1 =md.produce();
		a1.prepare();
		Factory yy = new YYFactory();     //鸳鸯火锅
		Hotpot a2 = yy.produce();
		a2.prepare();
		Factory yr= new YRFactory();  //羊肉火锅
		Hotpot a3 =yr.produce();
		a3.prepare();

	}

}
//接口
interface Hotpot{
	void prepare();
}
class MD implements Hotpot{

	@Override
	public void prepare() {
		System.out.println("准备毛肚火锅");
	}

}
class YY implements Hotpot{

	@Override
	public void prepare() {
		System.out.println("准备鸳鸯火锅");
	}
}
class YR implements Hotpot{

	@Override
	public void prepare() {
		System.out.println("准备羊肉火锅");
	}

}
//抽象火锅工厂类

abstract class Factory  {
	public abstract Hotpot produce();
}
//羊肉火锅工厂
class MDFactory extends Factory{
	public Hotpot produce() {
		return new MD();
	}
}
//鸳鸯火锅工厂
class YYFactory extends Factory{
	public Hotpot produce() {
		return new YY();
	}
}
//羊肉火锅工厂
class YRFactory extends Factory{
	public Hotpot produce() {
		return new YR();
	}
}
