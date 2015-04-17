/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.goes.smartparking;

import com.rapplogic.xbee.api.PacketListener;
import com.rapplogic.xbee.api.XBee;
import com.rapplogic.xbee.api.XBeeAddress16;
import com.rapplogic.xbee.api.XBeeException;
import com.rapplogic.xbee.api.XBeeResponse;
import com.rapplogic.xbee.api.wpan.TxRequest16;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author matheusgoes
 */
@WebServlet(name = "controlServlet", urlPatterns = {"/controlServlet"})
public class controlServlet extends HttpServlet {

    private XBee xbee = new XBee();
    boolean estadoVerde = false;
    private ArrayList<ArrayList<Integer>> pacotes = new ArrayList<ArrayList<Integer>>();
    //Vaga vaga = new Vaga("Vaga 1", "86120", '0');
    //ArrayList<Vaga> vagas = new ArrayList<Vaga>();
    ArrayList<XbeeDB> vagas;
    Mensagem xbeeMsg;
    MensagemDAO xmdao;
    XbeeDAO xDAO;
    String mensagem = "";

    int idLiberar, idReservar, valueLiberar = 0, valueReservar = 1;

    public ArrayList<ArrayList<Integer>> getPacotes() {
        return pacotes;
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int taxa = 0;
        String porta = null;

        String acao = request.getParameter("acao");

        switch (acao) {
            case "conectar":
                if (!request.getParameter("baud").equals(null)) {
                    taxa = Integer.parseInt(request.getParameter("baud"));
                }
                if (!request.getParameter("porta").equals(null)) {
                    porta = request.getParameter("porta");
                }

                if ((!porta.isEmpty()) && (taxa != 0)) {
                    try {
                        getXbee().open(porta, taxa);
                        xDAO = new XbeeDAO();
                        vagas = xDAO.getAll();
                    } catch (XBeeException ex) {
                        Logger.getLogger(controlServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    XBeeAddress16 destination = new XBeeAddress16(255, 255);
                    int[] payload;
                    String t = "3";
                    payload = new int[t.length()];
                    for (int j = 0; j < t.length(); j++) {
                        payload[j] = (int) t.charAt(j);
                    }
                    TxRequest16 tx = new TxRequest16(destination, payload);
                    try {
                        getXbee().sendAsynchronous(tx);
                    } catch (XBeeException ex) {
                        Logger.getLogger(controlServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    xbee.addPacketListener(new PacketListener() {

                        @Override

                        public void processResponse(XBeeResponse response) {
                            ArrayList<Integer> pacote = new ArrayList<Integer>();
                            pacote.add(response.getApiId().getValue());
                            int[] x = response.getProcessedPacketBytes();
                            for (int i = 0; i < x.length; i++) {
                                pacote.add(x[i]);
                            }
                            pacotes.add(pacote);
                            int msg;
                            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            java.util.Date date = new java.util.Date();
                            String dt = dateFormat.format(date);
                            String data = dt;
                            String remetente = pacote.get(4) + "" + pacote.get(5);

                            for (int i = 8; i < pacote.size() - 1; i++) {
                                msg = pacote.get(i).intValue();
                                for (int k = 0; k < vagas.size(); k++) {
                                    if ((vagas.get(k).getIdString().equals(remetente))) {

                                        if (vagas.get(k).getEstado() != msg) {
                                            vagas.get(k).setEstado(msg);
                                            xDAO.updateEstado(vagas.get(k));
                                        }
                                    }
                                }
                            }

                        }

                    }
                    );

                    if (xbee.isConnected()) {
                        RequestDispatcher rd = request.getRequestDispatcher("/conectado.jsp");
                        rd.forward(request, response);
                    } else {
                        RequestDispatcher rd = request.getRequestDispatcher("/error.jsp");
                        rd.forward(request, response);
                    }
                }
                break;
            case "desconectar":
                XBeeAddress16 destination = new XBeeAddress16(255, 255);
                int[] payload;
                String t = "2";
                payload = new int[t.length()];
                for (int j = 0; j < t.length(); j++) {
                    payload[j] = (int) t.charAt(j);
                }
                TxRequest16 tx = new TxRequest16(destination, payload);
                try {
                    getXbee().sendAsynchronous(tx);

                } catch (XBeeException ex) {
                    Logger.getLogger(controlServlet.class
                            .getName()).log(Level.SEVERE, null, ex);
                }

                xbee.close();
                RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
                break;

            case "reservar":
                xDAO.updateReserva(valueReservar, idReservar);
                rd = request.getRequestDispatcher("conectado.jsp");
                rd.forward(request, response);
                break;
            case "liberar":
                xDAO.updateReserva(valueLiberar, idLiberar);
                rd = request.getRequestDispatcher("conectado.jsp");
                rd.forward(request, response);
                break;
            case "verDados":
                try (PrintWriter out = response.getWriter()) {
                    out.println(up);
                    vagas = xDAO.getAll();

                    for (int i = 1; i < vagas.size(); i++) {
                        mensagem += "<td>" + vagas.get(i).getLocal() + "</td>";
                        if (vagas.get(i).getEstado() == 51) { //msg ==3
                            mensagem += "<td> Sol </td>";
                            if (vagas.get(i).getStatusReserva() == 0) {
                                mensagem += "<td><form action=\"controlServlet\" method=\"POST\">\n"
                                        + "         <input type=\"hidden\" name=\"acao\" value=\"reservar\"/>\n"
                                        + "         <input type=\"submit\" value=\"Reservar\" />\n"
                                        + "       </form> </td></tr>";
                                idReservar = vagas.get(i).getId();
                            }else{
                                 mensagem += "<td><form action=\"controlServlet\" method=\"POST\">\n"
                                        + "         <input type=\"hidden\" name=\"acao\" value=\"liberar\"/>\n"
                                        + "         <input type=\"submit\" value=\"Liberar\" />\n"
                                        + "       </form> </td></tr>";
                                idReservar = vagas.get(i).getId();
                            }
                        } else if (vagas.get(i).getEstado() == 50) { //msg ==2
                            mensagem += "<td> Sombra </td>";
                            if (vagas.get(i).getStatusReserva() == 0) {
                                mensagem += "<td><form action=\"controlServlet\" method=\"POST\">\n"
                                        + "         <input type=\"hidden\" name=\"acao\" value=\"reservar\"/>\n"
                                        + "         <input type=\"submit\" value=\"Reservar\" />\n"
                                        + "       </form> </td></tr>";
                                idReservar = vagas.get(i).getId();

                            }else{
                                 mensagem += "<td><form action=\"controlServlet\" method=\"POST\">\n"
                                        + "         <input type=\"hidden\" name=\"acao\" value=\"liberar\"/>\n"
                                        + "         <input type=\"submit\" value=\"Liberar\" />\n"
                                        + "       </form> </td></tr>";
                                idReservar = vagas.get(i).getId();
                            }
                        } else if (vagas.get(i).getEstado() == 49) { //msg == 1
                            mensagem += "<td> Ocupada </td>";
                            mensagem += "<td> --- </td></tr>";

                        } else {
                            mensagem += "<td> - </td>";
                            mensagem += "<td> - </td></tr>";
                        }

                        
                    }
                    out.println(mensagem);
                    out.println(down);
                    mensagem = "";
                }
                break;

            default:
                RequestDispatcher rd2 = request.getRequestDispatcher("/error.jsp");
                rd2.forward(request, response);
        }
    }

    /**
     * @return the xbee
     */
    public XBee getXbee() {
        return xbee;
    }

    private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        java.util.Date date = new java.util.Date();
        return dateFormat.format(date);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    String up = "<!DOCTYPE html>\n"
            + "<head>\n"
            + "    <meta charset=\"utf-8\">\n"
            + "    <title>e-Parking :: Home</title>\n"
            + "    <style type=\"text/css\">\n"
            + "        <!--\n"
            + "        body {\n"
            + "            background-image: url(images/bg.png);\n"
            + "\n"
            + "        }\n"
            + "        -->\n"
            + "    </style>\n"
            + "    <link href=\"css/style.css\" rel=\"stylesheet\" type=\"text/css\" />\n"
            + "    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/demo.css\" />   \n"
            + "</head>\n"
            + "\n"
            + "<body>\n"
            + "\n"
            + "    <div class=\"content\">\n"
            + "        <header>\n"
            + "\n"
            + "            <div id=\"logo\"><a href=\"index.jsp\"><img src=\"images/logo.png\" alt=\"Logo here\" width=\"200px\" height=\"80px\" /></a> </div>\n"
            + "            <div id=\"dev\" style=\"float: right;\"> Desenvolvido por: Matheus Goes </div> \n"
            + "\n"
            + "        </header>\n"
            + "\n"
            + "\n"
            + "        <section>\n"
            + "            <div id=\"navigation\" >\n"
            + "                <a href=\"conectado.jsp\">Inicio </a>\n"
            + "                |<a href=\"sobre.jsp\"> Sobre </a>\n"
            + "                |<a href=\"contato.jsp\"> Contato</a>\n"
            + "\n"
            + "            </div>"
            + "                <div class=\"transbox\">\n"
            + "                    <center><table border=1>\n"
            + "                            <thead>\n"
            + "                                <tr style=\"background-color: gray; color: white;\"> \n"
            + "                                    <td>Remetente</td>\n"
            + "                                    <td>Status</td>\n"
            + "                                    <td></td>\n"
            + "                                </tr>\n"
            + "                            <thead>\n"
            + "                            <tbody>";

    String down = "                            </tbody>\n"
            + "                        </table></center>\n"
            + "                </div>\n"
            + "            </section>\n"
            + "        </div>\n"
            + "    </div>\n"
            + "</body>\n"
            + "</html>";
}
