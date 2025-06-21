import java.io.File;
import java.util.HashMap;
import java.util.Map;

// ============================================================================
// PATRÓN FACTORY METHOD
// ============================================================================

/**
 * Clase abstracta que define la interfaz para los generadores de reportes
 */
abstract class ReportGenerator {
    
    /**
     * Método abstracto que debe ser implementado por cada generador específico
     */
    public abstract Report generate(Object data, StylingOptions styling);
    
    /**
     * Método común para procesar datos
     */
    protected Object processData(Object data) {
        System.out.println("Procesando datos del reporte...");
        return data;
    }
    
    /**
     * Método común para aplicar estilos
     */
    protected Object applyStyling(Object content, StylingOptions styling) {
        System.out.println("Aplicando estilos: " + styling.getColor() + ", " + styling.getFontFamily());
        return content;
    }
}

/**
 * Generador específico para reportes PDF
 */
class PDFReportGenerator extends ReportGenerator {
    
    @Override
    public Report generate(Object data, StylingOptions styling) {
        System.out.println("Generando reporte PDF...");
        
        Object processedData = processData(data);
        Object styledContent = applyStyling(processedData, styling);
        
        // Simulación de creación de PDF
        createPDFDocument();
        applyPDFStyling(styling);
        
        return new Report(styledContent, "PDF", styling);
    }
    
    private void createPDFDocument() {
        System.out.println("Creando documento PDF...");
    }
    
    private void applyPDFStyling(StylingOptions styling) {
        System.out.println("Aplicando estilos específicos de PDF...");
    }
}

/**
 * Generador específico para reportes Excel
 */
class ExcelReportGenerator extends ReportGenerator {
    
    @Override
    public Report generate(Object data, StylingOptions styling) {
        System.out.println("Generando reporte Excel...");
        
        Object processedData = processData(data);
        Object styledContent = applyStyling(processedData, styling);
        
        // Simulación de creación de Excel
        createExcelWorkbook();
        applyExcelStyling(styling);
        
        return new Report(styledContent, "Excel", styling);
    }
    
    private void createExcelWorkbook() {
        System.out.println("Creando libro de Excel...");
    }
    
    private void applyExcelStyling(StylingOptions styling) {
        System.out.println("Aplicando estilos específicos de Excel...");
    }
}

/**
 * Generador específico para reportes Word
 */
class WordReportGenerator extends ReportGenerator {
    
    @Override
    public Report generate(Object data, StylingOptions styling) {
        System.out.println("Generando reporte Word...");
        
        Object processedData = processData(data);
        Object styledContent = applyStyling(processedData, styling);
        
        // Simulación de creación de Word
        createWordDocument();
        applyWordStyling(styling);
        
        return new Report(styledContent, "Word", styling);
    }
    
    private void createWordDocument() {
        System.out.println("Creando documento Word...");
    }
    
    private void applyWordStyling(StylingOptions styling) {
        System.out.println("Aplicando estilos específicos de Word...");
    }
}

/**
 * Factory Method para crear generadores de reportes
 */
class ReportFactory {
    
    private static final Map<String, ReportGenerator> generators = new HashMap<>();
    
    static {
        initializeGenerators();
    }
    
    /**
     * Método factory que crea el generador apropiado según el tipo
     */
    public static ReportGenerator createGenerator(String reportType) {
        ReportGenerator generator = generators.get(reportType.toLowerCase());
        if (generator == null) {
            throw new IllegalArgumentException("Tipo de reporte no soportado: " + reportType);
        }
        return generator;
    }
    
    /**
     * Registra un nuevo generador de reportes
     */
    public static void registerGenerator(String reportType, ReportGenerator generator) {
        generators.put(reportType.toLowerCase(), generator);
    }
    
    private static void initializeGenerators() {
        generators.put("pdf", new PDFReportGenerator());
        generators.put("excel", new ExcelReportGenerator());
        generators.put("word", new WordReportGenerator());
    }
}

// ============================================================================
// PATRÓN ADAPTER
// ============================================================================

/**
 * Interfaz común para servicios de notificación
 */
interface NotificationService {
    boolean sendNotification(String recipient, String message, File attachment);
    boolean isAvailable();
    String getServiceName();
}

/**
 * Implementación del servicio de notificación por email
 */
class EmailNotificationService implements NotificationService {
    
    private String smtpServer;
    private int port;
    private String username;
    private String password;
    
    public EmailNotificationService(String smtpServer, int port, String username, String password) {
        this.smtpServer = smtpServer;
        this.port = port;
        this.username = username;
        this.password = password;
    }
    
    @Override
    public boolean sendNotification(String recipient, String message, File attachment) {
        System.out.println("Enviando email a: " + recipient);
        
        if (!validateEmail(recipient)) {
            System.out.println("Email inválido: " + recipient);
            return false;
        }
        
        configureSMTP();
        return sendEmail(recipient, "Reporte Generado", message, attachment);
    }
    
    @Override
    public boolean isAvailable() {
        return true; // Simulación de verificación de disponibilidad
    }
    
    @Override
    public String getServiceName() {
        return "Email";
    }
    
    private void configureSMTP() {
        System.out.println("Configurando SMTP: " + smtpServer + ":" + port);
    }
    
    private boolean sendEmail(String to, String subject, String body, File attachment) {
        System.out.println("Email enviado exitosamente a: " + to);
        return true;
    }
    
    private boolean validateEmail(String email) {
        return email != null && email.contains("@");
    }
}

/**
 * API externa de WhatsApp (simulada)
 */
class WhatsAppAPI {
    public boolean sendMessage(String phoneNumber, String message) {
        System.out.println("WhatsApp API: Enviando mensaje a " + phoneNumber);
        return true;
    }
    
    public boolean sendFile(String phoneNumber, File file) {
        System.out.println("WhatsApp API: Enviando archivo a " + phoneNumber);
        return true;
    }
    
    public String getStatus() {
        return "Connected";
    }
}

/**
 * Adapter para integrar WhatsApp con el sistema
 */
class WhatsAppAdapter implements NotificationService {
    
    private WhatsAppAPI whatsappAPI;
    private String apiKey;
    private String phoneNumber;
    
    public WhatsAppAdapter(String apiKey, String phoneNumber) {
        this.apiKey = apiKey;
        this.phoneNumber = phoneNumber;
        this.whatsappAPI = new WhatsAppAPI(); // En realidad se inicializaría con la API real
    }
    
    @Override
    public boolean sendNotification(String recipient, String message, File attachment) {
        System.out.println("Enviando notificación WhatsApp a: " + recipient);
        
        if (!validatePhoneNumber(recipient)) {
            System.out.println("Número de teléfono inválido: " + recipient);
            return false;
        }
        
        boolean messageSent = sendWhatsAppMessage(recipient, message);
        boolean fileSent = true;
        
        if (attachment != null) {
            fileSent = sendWhatsAppFile(recipient, attachment);
        }
        
        return messageSent && fileSent;
    }
    
    @Override
    public boolean isAvailable() {
        return "Connected".equals(whatsappAPI.getStatus());
    }
    
    @Override
    public String getServiceName() {
        return "WhatsApp";
    }
    
    private boolean sendWhatsAppMessage(String phoneNumber, String message) {
        return whatsappAPI.sendMessage(phoneNumber, message);
    }
    
    private boolean sendWhatsAppFile(String phoneNumber, File file) {
        return whatsappAPI.sendFile(phoneNumber, file);
    }
    
    private boolean validatePhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\+?[0-9]{10,15}");
    }
}

/**
 * API externa de Telegram (simulada)
 */
class TelegramAPI {
    public boolean sendMessage(String chatId, String message) {
        System.out.println("Telegram API: Enviando mensaje al chat " + chatId);
        return true;
    }
    
    public boolean sendDocument(String chatId, File file) {
        System.out.println("Telegram API: Enviando documento al chat " + chatId);
        return true;
    }
    
    public BotInfo getBotInfo() {
        return new BotInfo("ReportBot", "1.0");
    }
}

/**
 * Información del bot de Telegram
 */
class BotInfo {
    private String name;
    private String version;
    
    public BotInfo(String name, String version) {
        this.name = name;
        this.version = version;
    }
    
    public String getName() { return name; }
    public String getVersion() { return version; }
}

/**
 * Adapter para integrar Telegram con el sistema
 */
class TelegramAdapter implements NotificationService {
    
    private TelegramAPI telegramAPI;
    private String botToken;
    private String chatId;
    
    public TelegramAdapter(String botToken, String chatId) {
        this.botToken = botToken;
        this.chatId = chatId;
        this.telegramAPI = new TelegramAPI(); // En realidad se inicializaría con la API real
    }
    
    @Override
    public boolean sendNotification(String recipient, String message, File attachment) {
        System.out.println("Enviando notificación Telegram a: " + recipient);
        
        if (!validateChatId(recipient)) {
            System.out.println("Chat ID inválido: " + recipient);
            return false;
        }
        
        boolean messageSent = sendTelegramMessage(recipient, message);
        boolean fileSent = true;
        
        if (attachment != null) {
            fileSent = sendTelegramDocument(recipient, attachment);
        }
        
        return messageSent && fileSent;
    }
    
    @Override
    public boolean isAvailable() {
        return telegramAPI.getBotInfo() != null;
    }
    
    @Override
    public String getServiceName() {
        return "Telegram";
    }
    
    private boolean sendTelegramMessage(String chatId, String message) {
        return telegramAPI.sendMessage(chatId, message);
    }
    
    private boolean sendTelegramDocument(String chatId, File file) {
        return telegramAPI.sendDocument(chatId, file);
    }
    
    private boolean validateChatId(String chatId) {
        return chatId != null && chatId.matches("-?[0-9]+");
    }
}

// ============================================================================
// PATRÓN DECORATOR
// ============================================================================

/**
 * Clase abstracta base para componentes de reporte
 */
abstract class ReportComponent {
    public abstract String render();
    public abstract Object getContent();
    public abstract StylingOptions getStyling();
}

/**
 * Componente básico del reporte
 */
class BasicReport extends ReportComponent {
    
    private Object content;
    private StylingOptions styling;
    
    public BasicReport(Object content, StylingOptions styling) {
        this.content = content;
        this.styling = styling;
    }
    
    @Override
    public String render() {
        return "Contenido básico del reporte: " + content.toString();
    }
    
    @Override
    public Object getContent() {
        return content;
    }
    
    @Override
    public StylingOptions getStyling() {
        return styling;
    }
    
    public void setContent(Object content) {
        this.content = content;
    }
    
    public void setStyling(StylingOptions styling) {
        this.styling = styling;
    }
}

/**
 * Decorador abstracto base
 */
abstract class ReportDecorator extends ReportComponent {
    
    protected ReportComponent component;
    
    public ReportDecorator(ReportComponent component) {
        this.component = component;
    }
    
    @Override
    public String render() {
        return component.render();
    }
    
    @Override
    public Object getContent() {
        return component.getContent();
    }
    
    @Override
    public StylingOptions getStyling() {
        return component.getStyling();
    }
    
    public abstract Object applyDecoration(Object content);
}

/**
 * Decorador para aplicar colores
 */
class ColorDecorator extends ReportDecorator {
    
    private String color;
    private String backgroundColor;
    
    public ColorDecorator(ReportComponent component, String color, String backgroundColor) {
        super(component);
        this.color = color;
        this.backgroundColor = backgroundColor;
    }
    
    @Override
    public String render() {
        Object decoratedContent = applyDecoration(component.getContent());
        return "Contenido con color " + color + " y fondo " + backgroundColor + ": " + decoratedContent.toString();
    }
    
    @Override
    public Object applyDecoration(Object content) {
        return applyColor(content.toString(), color);
    }
    
    private String applyColor(String text, String color) {
        return "[" + color + "]" + text + "[/" + color + "]";
    }
    
    private String applyBackgroundColor(String text, String backgroundColor) {
        return "[" + backgroundColor + " background]" + text + "[/" + backgroundColor + " background]";
    }
}

/**
 * Decorador para aplicar fuentes
 */
class FontDecorator extends ReportDecorator {
    
    private String fontFamily;
    private int fontSize;
    private String fontWeight;
    
    public FontDecorator(ReportComponent component, String fontFamily, int fontSize, String fontWeight) {
        super(component);
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.fontWeight = fontWeight;
    }
    
    @Override
    public String render() {
        Object decoratedContent = applyDecoration(component.getContent());
        return "Contenido con fuente " + fontFamily + " tamaño " + fontSize + ": " + decoratedContent.toString();
    }
    
    @Override
    public Object applyDecoration(Object content) {
        return applyFont(content.toString(), fontFamily, fontSize, fontWeight);
    }
    
    private String applyFont(String text, String fontFamily, int fontSize, String fontWeight) {
        return "[" + fontFamily + ":" + fontSize + ":" + fontWeight + "]" + text + "[/font]";
    }
}

/**
 * Decorador para aplicar bordes
 */
class BorderDecorator extends ReportDecorator {
    
    private String borderStyle;
    private int borderWidth;
    private String borderColor;
    
    public BorderDecorator(ReportComponent component, String borderStyle, int borderWidth, String borderColor) {
        super(component);
        this.borderStyle = borderStyle;
        this.borderWidth = borderWidth;
        this.borderColor = borderColor;
    }
    
    @Override
    public String render() {
        Object decoratedContent = applyDecoration(component.getContent());
        return "Contenido con borde " + borderStyle + " " + borderWidth + "px " + borderColor + ": " + decoratedContent.toString();
    }
    
    @Override
    public Object applyDecoration(Object content) {
        return applyBorder(content, borderStyle, borderWidth, borderColor);
    }
    
    private Object applyBorder(Object content, String borderStyle, int borderWidth, String borderColor) {
        return "[" + borderStyle + ":" + borderWidth + ":" + borderColor + "]" + content.toString() + "[/border]";
    }
}

// ============================================================================
// CLASES DE SOPORTE
// ============================================================================

/**
 * Clase que representa un reporte
 */
class Report {
    
    private Object content;
    private String format;
    private StylingOptions styling;
    
    public Report(Object content, String format, StylingOptions styling) {
        this.content = content;
        this.format = format;
        this.styling = styling;
    }
    
    public Object getContent() {
        return content;
    }
    
    public String getFormat() {
        return format;
    }
    
    public StylingOptions getStyling() {
        return styling;
    }
    
    public void setContent(Object content) {
        this.content = content;
    }
    
    @Override
    public String toString() {
        return "Report{" +
                "format='" + format + '\'' +
                ", content=" + content +
                ", styling=" + styling +
                '}';
    }
}

/**
 * Clase que representa las opciones de estilo
 */
class StylingOptions {
    
    private String fontFamily;
    private int fontSize;
    private String color;
    private String backgroundColor;
    private String borderStyle;
    private int borderWidth;
    private String borderColor;
    
    public StylingOptions() {
        this.fontFamily = "Arial";
        this.fontSize = 12;
        this.color = "black";
        this.backgroundColor = "white";
        this.borderStyle = "none";
        this.borderWidth = 0;
        this.borderColor = "black";
    }
    
    public StylingOptions(String fontFamily, int fontSize, String color) {
        this();
        this.fontFamily = fontFamily;
        this.fontSize = fontSize;
        this.color = color;
    }
    
    // Getters
    public String getFontFamily() { return fontFamily; }
    public int getFontSize() { return fontSize; }
    public String getColor() { return color; }
    public String getBackgroundColor() { return backgroundColor; }
    public String getBorderStyle() { return borderStyle; }
    public int getBorderWidth() { return borderWidth; }
    public String getBorderColor() { return borderColor; }
    
    // Setters
    public void setFontFamily(String fontFamily) { this.fontFamily = fontFamily; }
    public void setFontSize(int fontSize) { this.fontSize = fontSize; }
    public void setColor(String color) { this.color = color; }
    public void setBackgroundColor(String backgroundColor) { this.backgroundColor = backgroundColor; }
    public void setBorderStyle(String borderStyle) { this.borderStyle = borderStyle; }
    public void setBorderWidth(int borderWidth) { this.borderWidth = borderWidth; }
    public void setBorderColor(String borderColor) { this.borderColor = borderColor; }
    
    @Override
    public String toString() {
        return "StylingOptions{" +
                "fontFamily='" + fontFamily + '\'' +
                ", fontSize=" + fontSize +
                ", color='" + color + '\'' +
                '}';
    }
}

/**
 * Clase principal que coordina todo el sistema
 */
class ReportManager {
    
    private ReportFactory factory;
    private NotificationService notificationService;
    
    public ReportManager() {
        this.factory = new ReportFactory();
    }
    
    /**
     * Genera un reporte usando el patrón Factory Method
     */
    public Report generateReport(Object data, String format, StylingOptions styling) {
        System.out.println("=== Generando Reporte ===");
        
        ReportGenerator generator = ReportFactory.createGenerator(format);
        Report report = generator.generate(data, styling);
        
        System.out.println("Reporte generado: " + report);
        return report;
    }
    
    /**
     * Envía un reporte usando el patrón Adapter
     */
    public boolean sendReport(Report report, String recipient, String service) {
        System.out.println("=== Enviando Reporte ===");
        
        // Crear el servicio de notificación apropiado
        switch (service.toLowerCase()) {
            case "email":
                notificationService = new EmailNotificationService("smtp.gmail.com", 587, "user@gmail.com", "password");
                break;
            case "whatsapp":
                notificationService = new WhatsAppAdapter("api_key_123", "+1234567890");
                break;
            case "telegram":
                notificationService = new TelegramAdapter("bot_token_456", "123456789");
                break;
            default:
                System.out.println("Servicio no soportado: " + service);
                return false;
        }
        
        String message = "Su reporte " + report.getFormat() + " está listo";
        File attachment = new File("reporte." + report.getFormat().toLowerCase());
        
        return notificationService.sendNotification(recipient, message, attachment);
    }
    
    /**
     * Demuestra el uso del patrón Decorator
     */
    public void demonstrateDecorator() {
        System.out.println("=== Demostrando Patrón Decorator ===");
        
        // Crear componente básico
        StylingOptions basicStyling = new StylingOptions("Arial", 12, "black");
        BasicReport basicReport = new BasicReport("Datos del proyecto", basicStyling);
        System.out.println("Reporte básico: " + basicReport.render());
        
        // Aplicar decoradores
        ColorDecorator colorDecorator = new ColorDecorator(basicReport, "blue", "lightblue");
        System.out.println("Con color: " + colorDecorator.render());
        
        FontDecorator fontDecorator = new FontDecorator(colorDecorator, "Times New Roman", 14, "bold");
        System.out.println("Con fuente: " + fontDecorator.render());
        
        BorderDecorator borderDecorator = new BorderDecorator(fontDecorator, "solid", 2, "black");
        System.out.println("Con borde: " + borderDecorator.render());
    }
}

// ============================================================================
// CLASE PRINCIPAL PARA DEMOSTRAR EL SISTEMA
// ============================================================================

public class SistemaReportes {
    
    public static void main(String[] args) {
        ReportManager manager = new ReportManager();
        
        // Datos de ejemplo
        Object projectData = "Informe del Proyecto XYZ - Q4 2024";
        StylingOptions styling = new StylingOptions("Arial", 12, "blue");
        
        System.out.println("=== SISTEMA DE GESTIÓN DE PROYECTOS ===\n");
        
        // 1. Demostrar Factory Method
        System.out.println("1. GENERANDO REPORTES (Factory Method):");
        Report pdfReport = manager.generateReport(projectData, "pdf", styling);
        Report excelReport = manager.generateReport(projectData, "excel", styling);
        Report wordReport = manager.generateReport(projectData, "word", styling);
        
        System.out.println();
        
        // 2. Demostrar Adapter
        System.out.println("2. ENVIANDO REPORTES (Adapter):");
        manager.sendReport(pdfReport, "usuario@email.com", "email");
        manager.sendReport(excelReport, "+1234567890", "whatsapp");
        manager.sendReport(wordReport, "123456789", "telegram");
        
        System.out.println();
        
        // 3. Demostrar Decorator
        manager.demonstrateDecorator();
        
        System.out.println("\n=== FIN DE LA DEMOSTRACIÓN ===");
    }
} 