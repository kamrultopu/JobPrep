/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package prov.engine.api;

import com.amazonaws.services.simpledb.model.ReplaceableAttribute;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import prov.engine.simpledb.ProvenanceManager;
import prov.engine.simpledb.SimpleDBHelper;
import prov.engine.util.AmazonCredentials;

/**
 *
 * @author Shams
 * sample api
 * AWSAccessKeyId=AKIAIM6WZ7YZFJSQNVKQ&DomainName=shams.uab.edu&ItemName=3&noOfAttribute=3&Attribute.1.Name=name&Attribute.1.Value=Nitu&Attribute.2.Name=age&Attribute.2.Value=30&Attribute.3.Name=address&Attribute.3.Value=13679 Bent Tree &Attribute.3.Replace=true
 */
public class PutAttr extends HttpServlet {

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
        String AWSAccessKeyId = request.getParameter("AWSAccessKeyId");
        String userId = request.getParameter("userId");
        AmazonCredentials credential = new AmazonCredentials(AWSAccessKeyId);
        String DomainName = request.getParameter("DomainName");
        String ItemName = request.getParameter("ItemName");
        int noOfAttribute = Integer.parseInt(request.getParameter("noOfAttribute"));
        
        List attributeList = new ArrayList(noOfAttribute);
        HashMap<String, String> inputMap = new HashMap<String, String>();
        
        for(int i=1; i <= noOfAttribute; i++)
        {
            String attName = request.getParameter("Attribute."+i+".Name");
            String attValue = request.getParameter("Attribute."+i+".Value");
            inputMap.put(attName, attValue);
            Boolean replacable = Boolean.FALSE;
            try{
                String replace = request.getParameter("Attribute."+i+".Replace");
                replacable = replace.equals("true") ? Boolean.TRUE : Boolean.FALSE;
            }
            catch(NullPointerException ne)
            {
                replacable = Boolean.FALSE;
            }
            ReplaceableAttribute attribute = new ReplaceableAttribute(attName, attValue, replacable);
            attributeList.add(attribute);
        }
        
        
        try (PrintWriter out = response.getWriter()) {
            
            try{
                SimpleDBHelper simpledbHelper = new SimpleDBHelper(credential, DomainName);
                ProvenanceManager provMananger = new ProvenanceManager(simpledbHelper);
                String returnMsg = provMananger.manageProvRecord(userId, ItemName, inputMap);
                
                if(!returnMsg.isEmpty()){
                    out.println(SUCCESS + "#"+returnMsg);
                    simpledbHelper.put(ItemName, attributeList);
                }
                else
                    out.println(ERROR + ":Prov Gen Failed!!");
                
            }
            catch(Exception ex){
                out.println(ERROR + "#"+ex.getLocalizedMessage());
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
