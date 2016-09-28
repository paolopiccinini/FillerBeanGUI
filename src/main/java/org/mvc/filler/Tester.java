package org.mvc.filler;


public class Tester {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void main(String[] args) {
//		RecuperaInfoSCIPAFIRequest request = new RecuperaInfoSCIPAFIRequest();
//		TheFiller filler = new TheFiller(request);
//		filler.fill();
//		System.out.println(new XStream().toXML(request));
		Test t = new Test();
		Filler filler = new Filler<Test>(t);
		filler.fill();
		System.out.println(t);
	}
	
	public static class Ruben {
		private int i;
		
		
		
		public int getI() {
			return i;
		}
		
		
		
		public void setI(int i) {
			this.i = i;
		}
		
		
		
		@Override
		public String toString() {
			return "Ruben [i=" + i + "]";
		}
		
	}
	
	public static class Test {
		private String s;
		private Integer i;
		private Ruben r;
		
		
		public String getS() {
			return s;
		}
		
		
		public void setS(String s) {
			this.s = s;
		}
		
		
		public Integer getI() {
			return i;
		}
		
		
		public void setI(Integer i) {
			this.i = i;
		}
		
		
		public Ruben getR() {
			return r;
		}
		
		
		public void setR(Ruben r) {
			this.r = r;
		}
		
		
		@Override
		public String toString() {
			return "Test [s=" + s + ", i=" + i + ", r=" + r + "]";
		}
		
		
	}
}


