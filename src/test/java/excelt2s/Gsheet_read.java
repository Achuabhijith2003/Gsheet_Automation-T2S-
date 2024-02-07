package excelt2s;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.SheetProperties;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.common.collect.Table.Cell;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;


public class Gsheet_read {


  private static final String APPLICATION_NAME = "Google Sheets API Java Quickstart";
  private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
  private static final String TOKENS_DIRECTORY_PATH = "tokens";

  /**
   * Global instance of the scopes required by this quickstart.
   * If modifying these scopes, delete your previously saved tokens/ folder.
   */
  private static final List<String> SCOPES =
      Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
  private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

  /**
   * Creates an authorized Credential object.
   *
   * @param HTTP_TRANSPORT The network HTTP Transport.
   * @return An authorized Credential object.
   * @throws IOException If the credentials.json file cannot be found.
   */
  private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
      throws IOException {
    // Load client secrets.
    InputStream in = Gsheet_read.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
    if (in == null) {
      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
    }
    GoogleClientSecrets clientSecrets =
        GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

    // Build flow and trigger user authorization request.
    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
        .setAccessType("offline")
        .build();
    LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
    return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
  }

  /**
   * Prints the names and majors of students in a sample spreadsheet:
   * https://docs.google.com/spreadsheets/d/1BxiMVs0XRA5nFMdKvBdBZjgmUUqptlbs74OgvE2upms/edit
   */

   String gsheetdata() throws IOException, GeneralSecurityException{
    try {
      final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
      final String spreadsheetId = "1SdliFgGDWHWgNrY0AmoK4CekLC6NcL_U4ZPPyNirE5M";
      String data=null;
      
      Sheets service =
          new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
              .setApplicationName(APPLICATION_NAME)
              .build();
              
               //
          //
          // ... (Authentication and connection setup)

// Get the sheet properties

//
//----- Name---------------

final String range = "Form Responses 1!A:B";

      ValueRange response = service.spreadsheets().values()
          .get(spreadsheetId, range)
          .execute();
          int numRows = response.getValues() != null ? response.getValues().size() : 0;

// Get the last row number

//System.out.println(numRows);

      List<List<Object>> values = response.getValues();
      if (values == null || values.isEmpty()) {
        System.out.println("No data found.");
      } else {
       // System.out.println("Name, Major");
       Object value;
       
       for (List row : values) {
        for (int i = 0; i < row.size(); i++) {
            value = row.get(i);
            if (value != null && (i == 0 || i == 4)) { // Print only columns A and E
               //System.out.println(value.toString());
            }
            else{
              data=value.toString();
            }
        }
    }  
   return data;
   
      }
      
    } catch (Exception e) {
      // TODO: handle exception
      System.err.println("No internet");
      System.err.println(e);
      return "Error";
    }
    //--------/Name----------



    //---------/Score----------


    //--------/Score------
    return "";
   }
  
  String []name=new String[2];
   boolean checksaved() throws IOException, GeneralSecurityException{
   
      if (name[0].equals(name[1])) {
        //  System.out.println(name[1]+" is already saved as "+name[0]); 
        return false;
      }else{
      
        name[0]=name[1];
        //  System.out.println(name[1]+" is already saved as "+name[0]);
           return true;
      }
    }
  public static void main(String[] args) throws IOException, GeneralSecurityException {
    Text2Speech t2s=new Text2Speech();
    Gsheet_read gsheet= new Gsheet_read();
    gsheet.name[0]="gsheet.gsheetdata()";
    
    while (true) {
      gsheet.name[1]=gsheet.gsheetdata();
      while (gsheet.checksaved()) {
        System.out.println(gsheet.gsheetdata());
        t2s.speak(gsheet.gsheetdata());
      }
    }
    
  }
}