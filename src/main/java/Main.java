import com.transformacaodedados.config.AppConfig;
import com.transformacaodedados.model.Procedure;
import com.transformacaodedados.service.FileProcessorService;
import com.transformacaodedados.service.PdfExtractorService;
import com.transformacaodedados.util.LoggerUtil;
import com.webscraping.controller.DocumentScraperController;
import com.webscraping.service.DocumentScraperService;
import com.webscraping.view.ConsoleView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.LogManager;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class Main {
    static {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        try (InputStream input = Main.class.getClassLoader().getResourceAsStream("logging.properties")) {
            if (input != null) {
                LogManager.getLogManager().readConfiguration(input);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        //webscraping();
        try {
            PdfExtractorService extractorService = new PdfExtractorService(AppConfig.getPdfPath());
            FileProcessorService processorService = new FileProcessorService();

            List<Procedure> procedures = extractorService.extractProcedure();
            processorService.saveToCSVAndZip(procedures, AppConfig.getCsvFileName(), AppConfig.getZipFileName());
        } catch (IOException e) {
            LoggerUtil.error("Erro na execução da aplicação", e);
            System.exit(1);
        }
    }
    private static void webscraping() {
        String targeUrl = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";

        DocumentScraperService service = new DocumentScraperService();
        ConsoleView view = new ConsoleView();
        DocumentScraperController controller = new DocumentScraperController(service, view);

        controller.scrapeAndProcess(targeUrl);
    }
}
