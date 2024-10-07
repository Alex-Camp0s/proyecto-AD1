package ejercicio_final;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que gestiona los datos de la clase Videojuego según las opciones que ofrece la clase Menu y lo que el
 * usuario desea hacer con dichos datos.
 * <p>
 * Contiene metodos para añadir videojuegos, eliminar videojuegos, mostrar todos los videojuegos guardados, guardar
 * los datos en un archivo y recuperar los datos de un archivo si cumple con el formato correspondiente.
 * <p>
 * Para manejar los datos, la clase contiene un ArrayList donde solo se pueden guardar videojuegos donde durante la
 * ejeución del programa se van añadiendo o eliminando los datos según lo que el usuario desea hacer. NO admitirá
 * duplicados de un mismo objeto.
 * <p>
 * Los metodos para guardar o recuperar datos de un archivo tienen las opciones de hacerlo en ficheros de texto,
 * binarios o ficheros xml.
 *
 * @author Alejandro Campos
 * @version 06102024A
 */
public class Gestor {

    private List<Videojuego> videojuegos;

    /**
     * Constructor que inicializa un objeto Gestor para que un usuario pueda interactuar con los datos que el programa
     * puede gestionar.
     * <p>
     * Al instanciar el objeto se crea un ArrayList de videojuegos.
     */
    public Gestor() {
        videojuegos = new ArrayList<>();
    }

    /**
     * Metodo que llama al metodo correspondiente según la opcion elegida por el usuario desde donde se cargarán los
     * datos al programa.
     * <p>
     * Correspondencia opciones a archivos:
     * - Fichero de texto: opcion 1
     * - Fichero binario: opcion 2
     * - Fichero xml: opcion 3
     * El resto de opciones devuelven mensaje de error
     *
     * @param opcion  opcion que refleja desde que tipo de archivo se quieren cargar los datos
     * @param archivo archivo desde el cual cargar los datos
     */
    public void cargar(int opcion, File archivo) {
        switch(opcion) {
            case 1:
                cargarTexto(archivo);
                break;
            case 2:
                cargarBinario(archivo);
                break;
            case 3:
                cargarXML(archivo);
                break;
            default:
                System.err.println("No existe ese tipo de fichero. Intentelo de nuevo");
        }
    }

    /**
     * Metodo que llama al metodo correspondiente según la opcion elegida por el usuario donde se guardarán los
     * datos del programa.
     * <p>
     * Correspondencia opciones a archivos:
     * - Fichero de texto: opcion 1
     * - Fichero binario: opcion 2
     * - Fichero xml: opcion 3
     * El resto de opciones devuelven mensaje de error
     *
     * @param opcion  opcion que refleja en que tipo de archivo se quieren guardar los datos
     * @param archivo archivo en el cual guardar los datos
     */
    public void guardar(int opcion, File archivo) {
        switch(opcion) {
            case 1:
                guardarTexto(archivo);
                break;
            case 2:
                guardarBinario(archivo);
                break;
            case 3:
                guardarXML(archivo);
                break;
            default:
                System.err.println("No existe ese tipo de fichero. Intentelo de nuevo");
        }
    }

    /**
     * Metodo que añade un videojuego al ArrayList del programa.
     * <p>
     * Se comprueba que el objeto no sea nulo y que no se encuentre ya en el ArrayList. En cualquiera de los casos se
     * devuelve un mensaje de error.
     *
     * @param v videojuego a añadir al ArrayList del programa.
     */
    public void addVideojuego(Videojuego v) {
        if(v == null) {
            System.err.println("Error: No se ha podido añadir el videojuego a la base de datos");
            return;
        }
        if(getVideojuego(v) != null) {
            System.out.println("El videojuego ya se encuentra en la base de datos");
            return;
        }
        videojuegos.add(v);
    }

    /**
     * Metodo que elimina un videjuego del ArrayList del programa.
     * <p>
     * Se comprueba que el objeto no sea nulo. Si es nulo se devuelve un mensaje de error.
     *
     * @param v videojuego a eliminar del ArrayList del programa.
     */
    public void elimVideojuego(Videojuego v) {
        if(v == null) {
            System.err.println("Error: El videojuego no se encuentra en la base de datos");
        }
        videojuegos.remove(v);
    }

    /**
     * Metodo que muestra todos los videojuegos que hay guardados en el ArrayList del programa.
     * <p>
     * Si el ArrayList está vacío se muestra un mensaje de que no hay ningún videojuego guardado.
     */
    public void listarVideojuegos() {
        if(videojuegos.isEmpty()) {
            System.out.println("No hay ningún videojuego en la base de datos");
        }
        for(Videojuego v : videojuegos) {
            System.out.println(v);
        }
    }

    /**
     * Metodo que busca un videojuego en el ArrayList del programa. Si lo encuentra lo devuelve para hacer las
     * operaciones correspondientes con él. Si no se encuentra se devuelve nulo y un mensaje de que no se ha encontrado.
     *
     * @param comprobar videojuego a conseguir de la base de datos
     *
     * @return videojuego con el nombre que se ha pasado por parametro si se ha encontrado, <code>null</code> si no
     * se ha encontrado
     */
    public Videojuego getVideojuego(Videojuego comprobar) {
        for(Videojuego v : videojuegos) {
            if(v.equals(comprobar)) {
                return v;
            }
        }
        return null;
    }

    /**
     * Metodo que carga los datos al programa desde un archivo de texto.
     * <p>
     * Para cargar los datos primero se comprueba que el archivo no esté vacío y si lo está se devuelve un mensaje
     * como que no se ha podido cargar ninun dato al programa.
     * Si el archivo no está vacío se comienza a leer el archivo línea por línea.
     * Para que se pueda leer correctamente, en cada línea los datos se deben de encontrar separados por puntos y
     * coma y debe de haber un total de cuatro datos por línea, ordenados de manera que primero se encuentre el
     * nombre del videojuego, después el año en el que se publicó, la nota que le da el usuario y finalmente si se
     * encuentra disponible en consola como <code>true</code> si es cierto, o <code>false</code> si no lo es.
     * <p>
     * Ejemplo de como se debería de ver un archivo:<br>
     * <code>
     * Videojuego 1;2020;8.3;false<br>
     * Videojuego 2;2021;9.4;true<br>
     * </code>
     * Si no siguen ese esquema el programa da error y no carga ninguno de los datos que se hayan podido leer hasta
     * el momento.
     *
     * @param archivo archivo desde el cual cargar los datos al programa
     */
    private void cargarTexto(File archivo) {
        if(archivo.length() == 0) {
            System.out.println("El archivo está vacío, no se ha cargado ningún dato");
            return;
        }
        try {
            BufferedReader in = new BufferedReader(new FileReader(archivo));
            List<Videojuego> leidos = new ArrayList<>();
            boolean error = false;
            String linea;
            while((linea = in.readLine()) != null) {
                Videojuego v = new Videojuego();
                String[] datos = linea.split(";");
                if(datos.length != 4) {
                    throw new IllegalArgumentException();
                }
                v.setNombre(datos[0]);
                v.setAnoPublicacion(Integer.parseInt(datos[1]));
                v.setNota(Double.parseDouble(datos[2]));
                v.setDisponibleEnConsola(Boolean.parseBoolean(datos[3]));
                leidos.add(v);
            }
            in.close();
            videojuegos = leidos;
        } catch(FileNotFoundException e) {
            System.err.println("No se ha encontrado el archivo");
        } catch(IOException e) {
            System.err.println("Ha ocurrido un error durante la carga de datos");
        } catch(IllegalArgumentException e) {
            System.err.println("El formato de los datos no es el adecuado, los datos no se han cargado");
        }
    }

    /**
     * Metodo que guarda los datos que se encuentren en ese momento en el ArrayList del programa.
     * <p>
     * Los datos se guardan con el siguiente formato, separados por puntos y coma y en cada línea un videojuego.
     * Primero se encuentra el nombre del videojuego, segundo el año en el que se publicó, tercero la nota que le da
     * el usuario y por último si está disponible en consola siendo <code>true</code> si es cierto o
     * <code>false</code> si no
     * <p>
     * Ejemplo de como se debería de ver un archivo:<br>
     * <code>
     * Videojuego 1;2020;8.3;false<br>
     * Videojuego 2;2021;9.4;true<br>
     * </code>
     *
     * @param archivo archivo en el cual guardar los datos
     */
    private void guardarTexto(File archivo) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(archivo));
            for(Videojuego v : videojuegos) {
                out.write(v.getNombre());
                out.write(";" + v.getAnoPublicacion());
                out.write(";" + v.getNota());
                out.write(";" + v.isDisponibleEnConsola());
                out.write("\n");
            }
            out.close();
        } catch(IOException e) {
            System.err.println("Ha ocurrido un error durante el guardado de datos");
        }
    }

    /**
     * Metodo desde el que cargar los datos de un archivo al programa.
     * <p>
     * Para cargar los datos primero se comprueba que el archivo no esté vacío y si lo está se devuelve un mensaje
     * como que no se ha podido cargar ninun dato al programa.
     * <p>
     * Si los datos guardados en el archivo no tienen el formato correspondiente, es decir ser un objeto
     * List<Videojuego> guardado en el archivo, los datos no se cargarán al programa.
     *
     * @param archivo archivo desde el cual cargar los datos al programa
     */
    private void cargarBinario(File archivo) {
        if(archivo.length() == 0) {
            System.out.println("El archivo está vacío, no se ha cargado ningún dato");
            return;
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(archivo)));
            videojuegos = (List<Videojuego>) in.readObject();
            in.close();
        } catch(FileNotFoundException e) {
            System.err.println("No se ha encontrado el archivo");
        } catch(IOException e) {
            System.err.println("Ha ocurrido un error durante la carga de datos");
        } catch(ClassNotFoundException e) {
            System.err.println("El formato de los datos no es el adecuado, no se han cargado los datos");
        }

    }

    /**
     * Metodo que guarda los datos que se encuentren en ese momento en el ArrayList del programa.
     * <p>
     * En el archivo se guarda directamente el ArrayList de videojuegos que haya en el momento guardado.
     *
     * @param archivo archivo en el cual guardar los datos
     */
    private void guardarBinario(File archivo) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(archivo)));
            out.writeObject(videojuegos);
            out.close();
        } catch(IOException e) {
            System.err.println("Ha ocurrido un error durante el guardado de datos");
        }
    }

    /**
     * Metodo desde el que cargar los datos de un archivo al programa.
     * <p>
     * Para cargar los datos primero se comprueba que el archivo no esté vacío y si lo está se devuelve un mensaje
     * como que no se ha podido cargar ninun dato al programa.
     * <p>
     * A la hora de la lectura se declaran los objetos correspondientes para cargar el archivo al programa
     * y se crea una lista donde guardar todos los videojuegos que se vayan leyendo.
     * Se buscan todos los nodos con el nombre videojuego y los guardo para recorrerlos y convertirlos en objetos
     * videojuego con sus datos correspondientes. Una vez leído el objeto videojuego se añade a la lista declarada
     * anteriormente.
     * Una vez leídos todos los videojuegos del archivo los añado al ArrayList principal del programa y ya puedo
     * manejar los datos desde el programa.
     *
     * @param archivo archivo desde el cual cargar los datos al programa
     */
    private void cargarXML(File archivo) {
        if(archivo.length() == 0) {
            System.out.println("El archivo está vacío, no se ha cargado ningún dato");
            return;
        }
        List<Videojuego> leido = new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(archivo);

            NodeList nList = doc.getElementsByTagName("videojuego");
            for(int i = 0; i < nList.getLength(); i++) {
                Element e = (Element) nList.item(i);
                String nombre = e.getElementsByTagName("titulo").item(0).getTextContent();
                int anoPublicacion =
                        Integer.parseInt(e.getElementsByTagName("ano_publicacion").item(0).getTextContent());
                double nota = Double.parseDouble(e.getElementsByTagName("nota").item(0).getTextContent());
                boolean enConsola = Boolean.parseBoolean(e.getAttribute("enConsola"));
                Videojuego v = new Videojuego(nombre, anoPublicacion, nota, enConsola);
                leido.add(v);
            }
            if(!leido.isEmpty()) {
                videojuegos = leido;
            } else {
                System.out.println("El archivo está vacío, no se ha cargado ningún dato");
            }
        } catch(ParserConfigurationException e) {
            System.out.println("Ha ocurrido un error, no se han cargado los datos");
        } catch(IOException e) {
            System.out.println("Ha ocurrido un error durante la lectura del archivo");
        } catch(SAXException e) {
            System.out.println("Ha ocurrido un error durante el cargado de datos");
        }
    }

    /**
     * Metodo que guarda los datos que se encuentren en ese momento en el ArrayList del programa.
     * <p>
     * Lo primero que hago es declarar todos los objetos necesarios para crear el archivo XML.
     * Luego declaro el elemento raíz del archivo que será el que contendrá todos los objetos videojuegos que tenga
     * guardados el programa en ese momento.
     * Y ya se pasa al guardado de cada uno de los objetos recorriendo el ArrayList del programa y por cada uno de
     * los atributos del objeto videojuego creamos un nodo, excepto en el caso de si está disponible en consolas que
     * lo guardaremos como atributo.
     * Si no ha ocurrido durante el guardado de datos, todos los datos que se hayan encontrado se vuelcan al archivo
     * XML.
     *
     * @param archivo archivo en el cual guardar los datos
     */
    private void guardarXML(File archivo) {
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();

            Element raiz = doc.createElement("biblioteca");
            doc.appendChild(raiz);

            for(Videojuego v : videojuegos) {
                Element videojuego = doc.createElement("videojuego");
                raiz.appendChild(videojuego);

                Element titulo = doc.createElement("titulo");
                titulo.appendChild(doc.createTextNode(v.getNombre()));
                videojuego.appendChild(titulo);

                Element anoPublicacion = doc.createElement("ano_publicacion");
                anoPublicacion.appendChild(doc.createTextNode(String.valueOf(v.getAnoPublicacion())));
                videojuego.appendChild(anoPublicacion);

                Element nota = doc.createElement("nota");
                nota.appendChild(doc.createTextNode(String.valueOf(v.getNota())));
                videojuego.appendChild(nota);

                Attr consola = doc.createAttribute("enConsola");
                consola.setValue(String.valueOf(v.isDisponibleEnConsola()));
                videojuego.setAttributeNode(consola);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(archivo);
            transformer.transform(source, result);
        } catch(ParserConfigurationException e) {
            System.out.println("Ha ocurrido un error durante la creación del archivo");
        } catch(TransformerConfigurationException e) {
            System.out.println("Ha ocurrido un error al guardar los datos");
        } catch(TransformerException e) {
            System.out.println("Ha ocurrido un error al generar los datos");
        }
    }

}
