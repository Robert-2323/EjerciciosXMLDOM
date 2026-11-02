package EJ2;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;


public class Ej2 {
    private static final String RUTA_DIR="Archivos/";
    private static final String RUTA_BOLSA="Bolsa.xml";
    private static final String RUTA_DATOS="datos.xml";

    public static void main(String[] args) {
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder= factory.newDocumentBuilder();

            Document docBolsa=builder.parse(new File(RUTA_DIR+RUTA_BOLSA));
            Document docDatos= builder.parse(new File(RUTA_DIR+RUTA_DATOS));
            Document bolsaActtualizada=builder.newDocument();

            Node operaciones=docDatos.getDocumentElement();

            NodeList operacion=docDatos.getElementsByTagName("Operacion");
            NodeList indices=docDatos.getElementsByTagName("Indice");
            NodeList nombres=docDatos.getElementsByTagName("Nombre");
            NodeList simbolos=docDatos.getElementsByTagName("Simbolo");
            NodeList precios=docDatos.getElementsByTagName("Precio");


            for(int i=0;i<operacion.getLength();i++){
                Element op=(Element)operacion.item(i);
                Element indice=(Element)indices.item(i);
                Element nombre=(Element)nombres.item(i);
                Element simbolo=(Element)simbolos.item(i);
                Element precio=(Element)precios.item(i);

                String indiceEmpresa=indice.getTextContent();
                String nomEmpresa=nombre.getTextContent();
                String simboloEmpresa=simbolo.getTextContent();
                String precioEmpresa=precio.getTextContent();


                if(op.getAttribute("id").equals("A")){
                    if (existeEmpresa(nomEmpresa)){
                        System.out.println("No se puede dar de alta una empresa ya existente");
                    }else {
                        crearEmpresaEnBolsa(indiceEmpresa,nomEmpresa,simboloEmpresa,precioEmpresa);
                        System.out.println("Empresa dada de alta correctamente");
                    }
                }

                if (op.getAttribute("id").equals("B")){
                    if (existeEmpresa(nomEmpresa)){
                        borrarElemento(nomEmpresa);
                    }else{
                        System.out.println("No se puede dar de baja a una empresa que no existe");
                    }
                }
            }


        }catch (Exception e){
            System.out.println("Error al acceder al documento xml");
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    public static boolean existeEmpresa(String empresaIn){
        boolean existe=false;
        try{

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder= factory.newDocumentBuilder();
            Document docBolsa=builder.parse(new File(RUTA_DIR+RUTA_BOLSA));

            NodeList nombres=docBolsa.getElementsByTagName("Nombre");


            for(int i=0;i<nombres.getLength();i++){
                Element nombre=(Element)nombres.item(i);
                String nombreEmpresa=nombre.getTextContent();
                if (nombreEmpresa.equals(empresaIn)){
                    existe=true;
                }
            }
        }catch (Exception e){
            System.out.println("Error al acceder al documento xml");
            System.out.println("ERROR: "+e.getMessage());
        }
        return existe;

    }

    public static void crearEmpresaEnBolsa(String indice,String nombre,String simbolo,String precio){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder= factory.newDocumentBuilder();

            Document docBolsa=builder.parse(new File(RUTA_DIR+RUTA_BOLSA));
            Element raiz=docBolsa.getDocumentElement();

            Element empresa=docBolsa.createElement("Empresa");
            empresa.setAttribute("indice",indice);
            raiz.appendChild(empresa);

            Element nom=docBolsa.createElement("Nombre");
            nom.setTextContent(nombre);
            empresa.appendChild(nom);

            Element simb=docBolsa.createElement("Simbolo");
            simb.setTextContent(simbolo);
            empresa.appendChild(simb);

            Element price=docBolsa.createElement("Precio");
            price.setTextContent(precio);
            empresa.appendChild(price);

        }catch (Exception e){
            System.out.println("Error al acceder al documento xml");
            System.out.println("ERROR: "+e.getMessage());
        }
    }

    public static void borrarElemento(String nombre){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder= factory.newDocumentBuilder();

            Document docBolsa=builder.parse(new File(RUTA_DIR+RUTA_BOLSA));


            NodeList nombres=docBolsa.getElementsByTagName("Nombre");

            for (int i=0;i< nombres.getLength();i++){
                Element nom=(Element)nombres.item(i);
                Node padre=nom.getParentNode();
                Node nomNode=padre.getFirstChild();
                String nombreEmpresa=nomNode.getTextContent();
                if (nombreEmpresa.equals(nombre)){
                    padre.removeChild(nomNode);
                }
            }




        }catch (Exception e){
            System.out.println("Error al acceder al documento xml");
            System.out.println("ERROR: "+e.getMessage());
        }
    }
}
