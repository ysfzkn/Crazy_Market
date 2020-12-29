import java.util.Random;

public class Customer 
{
	public Customer()
	{
		Random r1 = new Random();
		this.arrivalTime = 2 * r1.nextDouble() ;
		this.serviceTime = 1 + (2) * r1.nextDouble();		
	}

	public double getarrivalTime()
	{
		return this.arrivalTime ;
	}

	public void setarrivalTime(double arrivalTime)
	{
		this.arrivalTime = arrivalTime;
	}

	public double getserviceTime()
	{
		return this.serviceTime ;
	}

	public void setserviceTime(double serviceTime)
	{
		this.serviceTime = serviceTime;
	}
	//datafield tiplerini degistirebilirsiniz
	// double yaptım çünkü bir müşteri mesela 1.3 sn de de gelebilir veya hizmet alabilir.
	double arrivalTime; 
	//musteri gelis zamani-
	//bekleme zamanini hesaplamada kullanabilirsiniz
	double removalTime;
	double serviceTime;
}
