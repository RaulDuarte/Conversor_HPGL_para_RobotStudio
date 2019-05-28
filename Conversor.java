/*
 * Programa desenvolvido para a disciplina de Funcamentos de Robotica. O programa lê arquivo de .txt com as 
 * coordenadas em pixel do desenho salvo no formato HPGL. O programa separa as coordenadas penUp (PU) e 
 * as coordenadas penDown (PD) e escreve na sintaxe usada pelo RobotStudio.
 */
package fundamentosRobotica;
import java.io.*;
import java.util.*;
import java.text.DecimalFormat; 

public class Fundamentos {
	static double temp1 = 0;
	static double temp2 = 0;
	static double temp3 = 0;
	static double temp4 = 0;
	
	public static void main(String[] args) {
		
		FileReader fr;
		BufferedReader br;
		StringTokenizer st, stPu, stPd;
		String token;
		int i, count, j, countPD;

		try 
		{
			//ler o arquivo .txt no diretorio
			File arquivo = new File("D:/Workspace_Eclipse/fundamentosRobotica/src/fundamentosRobotica/EradoGelo.txt");
			fr = new FileReader(arquivo);
			br = new BufferedReader(fr);

			while(br.ready())
			{
				st = new StringTokenizer(br.readLine(), ";");
				count = st.countTokens();
				for(i=0; i<count; i++)
				{
					token = st.nextToken();
					if(token.substring(0, 2).compareTo("PU") == 0)		
					{
						stPu = new StringTokenizer(token.substring(2), ",");

						escreve_PU(stPu.nextToken(), stPu.nextToken());
					}
					else if(token.substring(0, 2).compareTo("PD") == 0)
					{
						stPd = new StringTokenizer(token.substring(2), ",");
						countPD = stPd.countTokens();
						for(j=0; j<countPD/2; j++)
						{
							escreve_PD(stPd.nextToken(), stPd.nextToken());
						}
					}
				}
			}
		}
		catch(IOException e){System.out.println(e.toString());}
	}
	
	static void escreve_PU(String n1, String n2){
		try{
			FileWriter fw = new FileWriter("D:/Workspace_Eclipse/fundamentosRobotica/src/fundamentosRobotica/posicao.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);

			int num1 =  Integer.parseInt(n1);
			int num2 = Integer.parseInt(n2);
			double number1 = num1/40.0;
			String numero1 = precisao(number1);
			double number2 = num2/40.0;
			String numero2 = precisao(number2);

			System.out.println("MoveJ Offs (P0, " + temp3 + ", " + temp4 + ", 20), V1000, z0, tool0;");
			bw.write("MoveJ Offs (P0, " + temp3 + "," + temp4 + ", 20), V1000, z0, tool0;");
			bw.newLine();
			bw.write("MoveJ Offs (P0, " + numero1 + "," + numero2 + ", 20), V1000, z0, tool0;");
			bw.newLine();

			bw.close();
			fw.close();

		}
		catch (IOException ex) {ex.printStackTrace();}
	}
	
	static void escreve_PD(String n1, String n2){
		try{
			FileWriter fw = new FileWriter("D:/Workspace_Eclipse/fundamentosRobotica/src/fundamentosRobotica/posicao.txt", true);
			BufferedWriter bw = new BufferedWriter(fw);
			
			int num1 =  Integer.parseInt(n1);
			int num2 = Integer.parseInt(n2);
			double number1 = (num1/40.0);
			double number2 = (num2/40.0);
			
			if(Math.abs(temp1-number1) > 0.5 || Math.abs(temp2-number2) > 0.5){
				temp1 = number1;
				temp2 = number2;
				temp3 = number1;
				temp4 = number2;
				String numero1 = precisao(number1);
				String numero2 = precisao(number2);

				System.out.println("MoveL Offs (P0," + numero1 + "," + numero2 + ", 0), V1000, z0, tool0;");
				bw.write("MoveL Offs (P0, " + numero1 + "," + numero2 + ", 0), V1000, z0, tool0;");
				bw.newLine();
			}
			
			bw.close();
			fw.close();
		}
		catch (IOException ex) {ex.printStackTrace();}
	}
	
	static String precisao(double number)
	{
		DecimalFormat df = new DecimalFormat("0.#");
		String numero = df.format(number);
		
		return (numero.replace(",", "."));
	}
}

