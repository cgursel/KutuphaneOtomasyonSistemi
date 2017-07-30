package UyeEkle;

import java.net.URL;
import javafx.scene.Node;
import java.sql.*;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import DatabaseBaglanti.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;

public class UyeEkleController implements Initializable
{
	@FXML
    private JFXTextField id;
    @FXML
    private JFXTextField name;
    @FXML
    private JFXTextField mobile;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXButton ekleButonu;
    @FXML
    private JFXButton iptalButonu;

    @FXML
    private AnchorPane rootPane;
    
   Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        conn=DatabaseConnection.BaglantiKur();
        if(conn==null)
        {
        	System.out.println("Ba�lant� Kurulamad�");
        }
    }
    
    @FXML
    private void uyeEkle(ActionEvent event) throws SQLException
    {
    	
    	PreparedStatement preparedStatement=null;
    	ResultSet rs=null;
    	
    	
    	String uyeId=id.getText();
    	String uyeIsim=name.getText();
    	String uyeMobile=mobile.getText();
    	String uyeEmail=email.getText();
    	
    	if (uyeId.isEmpty() || uyeId.isEmpty() || uyeMobile.isEmpty() || uyeEmail.isEmpty()) 
    	{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("T�m alanlar� doldurunuz");
            alert.showAndWait();
            return;
        }
    	
    	String query="INSERT INTO MEMBER(ID,NAME,MOBILE,MAIL) VALUES(?,?,?,?)";
		
		try 
		{
			preparedStatement=conn.prepareStatement(query);
			preparedStatement.setString(1, uyeId);
			preparedStatement.setString(2, uyeIsim);
			preparedStatement.setString(3, uyeMobile);
			preparedStatement.setString(4, uyeEmail);
			rs=preparedStatement.executeQuery();
			
			if(rs!=null)
			{
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("�ye Ekleme ��lemleri");
            alert.setContentText("Ekleme Ba�ar�yla Sonu�land�");
            alert.showAndWait();
			}
			
			
		} 
		catch (Exception e) 
		{
			/*Burada ekleme i�leminde ba�ar�s�zl�k varsa o mesaj yazd�r�l�yor.�rne�in veritaban�nda
			 * id alan� primary key ve ayn� id den eklemeye �al���rsan�z ekleme yapmaz ve rs null d�ner
			 * ama try catch �al��t�raca�� i�in try i�inde rs=preparedStatement.executeQuery(); bu kod �al��mad��� an
			 * direk olarak catch blo�una atlar o y�zden ba�ar�s�zl�k hatas�n� yukar�da rs==null diye if olu�turmadan
			 * burada kulland�k
			*/
			Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("�ye Ekleme ��lemleri");
            alert.setContentText("BA�ARISIZ OLUNDU");
            alert.showAndWait();
			e.printStackTrace();
		}
		finally
		{
			
			preparedStatement.close();
			
			id.setText("");
			name.setText("");
			mobile.setText("");
			email.setText("");
			
		}
		
	}
    			
    					
    	
    	
    
    
    @FXML
    private void UyeIptal(ActionEvent event)
    {
    	( (Node)event.getSource() ).getScene().getWindow().hide();;
    }
    
    
    
    
    
    
}
