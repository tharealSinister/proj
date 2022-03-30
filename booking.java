package paractice;
import java.util.*;
//apun ka
public class Booking {
public static void main(String[] args) {
	List<Taxi>taxis=createTaxis(4);
	Scanner s=new Scanner(System.in);
	int id=1;
	while(true)
	{
		System.out.println("Press Book for booking");
		System.out.println("Press Display for displaying details");	
		int choice=s.nextInt();
		switch(choice)
		{
		case 1:{
			int CustomerID=id;
			System.out.println("Enter Pickup:");
			char pickupPoint=s.next().charAt(0);
			System.out.println("Enter Drop");
			char dropPoint=s.next().charAt(0);
			System.out.println("Enter Pickup Time:");
			int pickupTime=s.nextInt();
			
			if(pickupPoint<'A'||pickupPoint>'F'||dropPoint<'A'||dropPoint>'F')
			{
				System.out.println("Enter valid point!");
				return;
			}
		  List<Taxi>freeTaxis=getFreeTaxis(taxis,pickupTime,pickupPoint);
			if(freeTaxis.size()==0)
			{
				System.out.println("No free Taxis!");
			    return;	
			}
			Collections.sort(freeTaxis,(a,b)->a.totalEarnings-b.totalEarnings);
			bookTaxi(id,pickupPoint,dropPoint,pickupTime,freeTaxis);
			id++;
			break;
		}
		case 2:
		{
		 for(Taxi t:taxis)
		 {
			 t.printTaxiDetails();
		 }
		 for(Taxi t:taxis)
		 {
			 t.printDetails();
		 }
		break;	 
	   }
		default:
			return;
}
}
}

public static List<Taxi> createTaxis(int n)
{
	List<Taxi>taxis=new ArrayList<Taxi>();
	for(int i=1;i<=n;i++)
	{
		Taxi t=new Taxi();
		taxis.add(t);
	}
	return taxis;
}
public static List<Taxi> getFreeTaxis(List<Taxi>taxis,int pickupTime,char pickupPoint)
{
	List<Taxi>freeTaxis=new ArrayList<Taxi>();
	for(Taxi t:taxis)
	{
		if(pickupTime>=t.freeTime&&Math.abs((t.currentSpot-'0')-(pickupPoint-'0'))<=pickupTime-t.freeTime)
		{
			freeTaxis.add(t);
		}
	}
	return freeTaxis;
	
}
public static void bookTaxi(int customerId,char pickupPoint,char dropPoint,int pickupTime,List<Taxi>freeTaxis)
{
	int min=Integer.MAX_VALUE;
	int distanceBetweenPickupandDrop=0;
	int earnings=0;
	int nextFreeTime=0;
	char nextSpot='Z';
	Taxi bookedTaxi=null;
	String tripDetail="";
	for(Taxi t:freeTaxis)
	{
		int distanceBetweenCustomerandTaxi=Math.abs((t.currentSpot-'0')-pickupPoint-'0')*15;
		if(distanceBetweenCustomerandTaxi<min)
		{
			bookedTaxi=t;
			distanceBetweenPickupandDrop=Math.abs((dropPoint-'0')-(pickupPoint-'0'))*15;
			earnings=100+(distanceBetweenPickupandDrop-5)*10;
			int dropTime=pickupTime+distanceBetweenPickupandDrop/15;
			nextSpot=dropPoint;
			nextFreeTime=dropTime;
			tripDetail=customerId+"            " +customerId+"            "+pickupPoint+"          "+dropPoint+"         "+pickupTime+"          "+dropTime+"          "+earnings;
			min=distanceBetweenCustomerandTaxi;
		}
	}
	bookedTaxi.setDetails(true, nextSpot, nextFreeTime, bookedTaxi.totalEarnings+earnings, tripDetail);
	System.out.println("Taxi "+bookedTaxi.id+"Booked!");
}
}
