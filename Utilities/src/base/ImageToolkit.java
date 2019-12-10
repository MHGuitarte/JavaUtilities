package base;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

public class ImageToolkit {

	public static byte[] imageToByte(String fileName) {
		try {
			File file = new File(fileName);
			BufferedImage image = ImageIO.read(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);

			byte[] imageInByte = baos.toByteArray();
			return imageInByte;
		} catch (IOException e) {
			return null; // TODO: Controlar el error con un throw Exception
		}
	}

	public static SerialBlob imageToBlob(String fileName) {
		try {
			SerialBlob blob = new SerialBlob(imageToByte(fileName));
			return blob;
		} catch (SQLException e) {
			return null; // TODO: Controlar el error con un throw Exception
		}
	}

}
