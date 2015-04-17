
<%@page import="com.tcc.xbeemonitor.Mensagem"%>
<%@page import="com.tcc.xbeemonitor.MensagemDAO"%>
<%@page import="gnu.io.RXTXCommDriver"%>
<%@page import="gnu.io.CommPortIdentifier"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.util.ArrayList"%>


<!DOCTYPE html>
<head>
    <meta charset="utf-8">
    <title>e-Parking :: Home</title>
    <style type="text/css">
        <!--
        body {
            background-image: url(images/bg.png);

        }
        -->
    </style>
    <link href="css/style.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="css/demo.css" />   
</head>

<body>

    <div class="content">
        <header>

            <div id="logo"><a href="index.jsp"><img src="images/logo.png" alt="Logo here" width="200px" height="80px" /></a> </div>
            <div id="dev" style="float: right;"> Desenvolvido por: Matheus Goes </div> 


        </header>
        

        <section>
        <div id="navigation" >
                <a href="#">Inicio </a>
                |<a href="sobre.jsp"> Sobre </a>
                |<a href="contato.jsp"> Contato</a>
        </div>
           <div class="transbox">
                    <h2>Conectar Xbee</h2>
                    <form name="Name Input Form" action="controlServlet" method="POST">
                        <label>Selecione a porta serial:</label>
                        <%
                            /*Mensagem mensagem = new Mensagem ("teste", 1122);
                            MensagemDAO mdao = new MensagemDAO();
                            if (mdao.insert(mensagem)){
                                System.err.println("gravado com sucesso");
                            }*/
                            
                            Enumeration pList = CommPortIdentifier.getPortIdentifiers();
                            ArrayList<String> p = new ArrayList<>();
                            // Process the list.
                            while (pList.hasMoreElements()) {
                                CommPortIdentifier cpi = (CommPortIdentifier) pList.nextElement();
                                if (cpi.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                                    p.add(cpi.getName());
                                }
                            }
                        %>
                        <select name="porta">
                            <%
                                for (int i = 0; i < p.size(); i++) {
                                    out.print("<option value='" + p.get(i) + "'>" + p.get(i) + "</option>");
                                }
                            %>
                        </select><br>
                        <label>Taxa (baudrates)</label>
                        <input type="number" name="baud" value="9600" style="color: #444"/><br>
                        <input type="hidden" name="acao" value="conectar"/><br>
                        <input type="submit" value="Conectar" />
                    </form>
                </div>
        </section>
    </div>

</body>
</html>
