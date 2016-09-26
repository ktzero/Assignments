package ssa;

public class Mainline {

	public static void main(String[] args) {
		
		CreateMajor major = new CreateMajor();
		
		major.openFactory();
		major.insertMajor("Sleeping", 200);
		major.insertMajor("Eating", 100);
		major.insertMajor("Underwater swimming", 1345);
		major.deleteMajor("Eating");
		major.updateMajor("Underwater swimming", "Underwater basketweaving");
		major.displayAll();
		major.closeFactory();
	}

}
