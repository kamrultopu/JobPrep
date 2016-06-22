/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.api;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import prov.engine.crypto.Base64;
import prov.engine.crypto.bas.BAS;
import prov.engine.crypto.bas.BASSigner;
import prov.engine.dal.ProvenanceBlockHandler;
import prov.engine.simpledb.ProvenanceManager;
import prov.engine.simpledb.SimpleDBHelper;
import prov.engine.util.AmazonCredentials;
import prov.engine.util.Config;

/**
 *
 * @author Shams
 */
public class Aggregate extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String SUCCESS = "SUCCESS";
        String ERROR = "ERROR";
        String blockId = request.getParameter("blockId");
        String sigma = request.getParameter("sigma");
        
        try (PrintWriter out = response.getWriter()) {
            try {
                String newSigma = "";

                ProvenanceBlockHandler handler = new ProvenanceBlockHandler();
                String currentSigma = handler.getProvChainSigmaofBlock(new Long(blockId));

                if (currentSigma.isEmpty()) {
                    newSigma = sigma;
                } else {
                    Pairing pairing = PairingFactory.getPairing(Config.Instanc().BASPrpertyFile());

                    byte[] curSigmabytes = Base64.decode(currentSigma);
                    byte[] sigmabytes = Base64.decode(sigma);
                    Field G1 = pairing.getG1();
                    Element curSigElement = G1.newRandomElement();
                    Element sigElement = G1.newRandomElement();

                    curSigElement.setFromBytes(curSigmabytes);
                    sigElement.setFromBytes(sigmabytes);
                    BAS serverBas = new BAS();
                    Element aggreSign = serverBas.aggreSign(curSigElement, sigElement);
                    newSigma = Base64.encodeBytes(aggreSign.toBytes());

                }
            
                handler.updateAggregateSignatur(newSigma, new Long(blockId));
                out.println(SUCCESS);
            } catch (Exception ex) {
                ex.printStackTrace();
                out.println(ERROR + "#" + ex.getLocalizedMessage());
            }
            

        }

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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
