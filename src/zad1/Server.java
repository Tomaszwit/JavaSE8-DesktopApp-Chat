/**
 *
 *  @author Witkowski Tomasz S12678
 *
 */

package zad1;


public class Server {
	static Server_model dane;
	static Server_GUI controler;
	static Server_view view;
  public static void main(String[] args) {
	 //MODEL - DANE
	  dane = new Server_model();
	 //WIDOK UI
	  view = new Server_view();
	  dane.addPropertyChangeListener(view);
	//KONTROLA
	  controler = new Server_GUI(dane, view);
  }
}
