package com.hfsgwt.server.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.Checksum;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;

import com.hfsgwt.server.ServicoException;

public final class RotinasZIP {
	private static Logger log = Logger.getLogger(RotinasZIP.class);
	private static RotinasZIP instancia;

	private RotinasZIP() {
		super();
	}

	public static RotinasZIP getInstancia() {
		if (instancia == null) {
			instancia = new RotinasZIP();
		}
		return instancia;
	}

	public void criarZIP(String nomeArquivoZip, String dirArquivos,
			ArrayList<String> arquivos) throws ServicoException {
		String[] arqs = (String[]) arquivos
				.toArray(new String[arquivos.size()]);
		this.criarZIP(nomeArquivoZip, dirArquivos, arqs);
	}

	/**
	 * Criar arquivo zipado
	 * 
	 * @param nomeArquivoZip
	 *            nome do arquivo a ser zipado
	 * @param arquivos
	 *            arquivos a serem zipados
	 */
	public void criarZIP(String nomeArquivoZip, String dirArquivos,
			String[] arquivos) throws ServicoException {
		// Cria um buffer para a leitura dos arquivos
		byte[] buf = new byte[1024];

		try {
			// Cria o arquivo ZIP
			String outFilename = nomeArquivoZip;
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					outFilename));

			// Comprime os arquivos
			for (int i = 0; i < arquivos.length; i++) {
				FileInputStream in = new FileInputStream(dirArquivos + "/"
						+ arquivos[i]);

				// Adiciona uma entrada ZIP para a stream de saída.
				out.putNextEntry(new ZipEntry(arquivos[i]));

				// Transfere bytes do arquivo para o arquivo ZIP
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				// Fecha a entrada
				out.closeEntry();
				in.close();
			}

			// Fecha o arquivo ZIP
			out.close();
		} catch (IOException e) {
			throw new ServicoException(log,
					"Erro de entrada/saída ao criar arquivo ZIP, "
							+ e.getMessage());
		}
	}

	public void criarZIP(String nomeArquivoZip, String dirArquivo,
			String[] dirArquivos, String[] arquivos) throws ServicoException {
		// Cria um buffer para a leitura dos arquivos
		byte[] buf = new byte[1024];
		FileInputStream in;

		try {
			// Cria o arquivo ZIP
			String outFilename = nomeArquivoZip;
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					outFilename));

			// Comprime os arquivos
			for (int i = 0; i < arquivos.length; i++) {
				if (dirArquivos[i].trim().length() == 0) {
					in = new FileInputStream(dirArquivo + "/" + dirArquivos[i]
							+ "/" + arquivos[i]);

					// Adiciona uma entrada ZIP para a stream de saída.
					out.putNextEntry(new ZipEntry(arquivos[i]));
				} else {
					in = new FileInputStream(dirArquivo + "/" + dirArquivos[i]
							+ "/" + arquivos[i]);

					// Adiciona uma entrada ZIP para a stream de saída.
					out.putNextEntry(new ZipEntry(dirArquivos[i] + "/"
							+ arquivos[i]));
				}
				// Transfere bytes do arquivo para o arquivo ZIP
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();

				// Fecha a entrada
				out.closeEntry();
			}

			// Fecha o arquivo ZIP
			out.close();
		} catch (IOException e) {
			throw new ServicoException(log,
					"Erro de entrada/saída ao criar arquivo ZIP, "
							+ e.getMessage());
		}
	}

	/**
	 * Listar arquivos num arquivo zipado
	 * 
	 * @param nomeArquivoZip
	 *            nome do arquivo zipado
	 * @return o nome dos arquivos zipados
	 */
	public ArrayList<String> listarZIP(String nomeArquivoZip)
			throws ServicoException {
		ArrayList<String> lista = new ArrayList<String>();
		try {
			ZipFile zf = new ZipFile(nomeArquivoZip);

			for (Enumeration<? extends ZipEntry> entries = zf.entries(); entries.hasMoreElements();) {
				String zipEntryName = ((ZipEntry) entries.nextElement())
						.getName();
				lista.add(zipEntryName);
			}
			zf.close();
		} catch (IOException e) {
			throw new ServicoException(log,
					"Erro de entrada/saída ao listar arquivo ZIP, "
							+ e.getMessage());
		}
		return lista;
	}

	/**
	 * Recupera os arquivos num arquivo zipado
	 * 
	 * @param nomeArquivoZip
	 *            nome do arquivo zipado
	 */
	public void recupeparZIP(String nomeArquivoZip, String dirArquivos)
			throws ServicoException {
		try {
			ZipInputStream in = new ZipInputStream(new FileInputStream(
					nomeArquivoZip));

			ZipEntry entry = in.getNextEntry();
			do {
				String outFilename = entry.getName();
				OutputStream out = new FileOutputStream(dirArquivos
						+ outFilename);

				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}

				out.close();

				entry = in.getNextEntry();

			} while (entry != null);

			in.close();
		} catch (IOException e) {
			throw new ServicoException(log,
					"Erro de entrada/saída ao recupepar arquivos ZIP, "
							+ e.getMessage());
		}
	}

	/**
	 * Calcula o Checksum CRC32 de um arquivo
	 * 
	 * @param arquivoNome
	 *            o nomes do arquivo
	 * @return o Checksum CRC32 de um arquivo
	 */
	public long calculaCRC32Arquivo(String arquivoNome) throws ServicoException {
		long checksum = -1;
		try {
			// Computa CRC32 checksum
			CheckedInputStream cis = new CheckedInputStream(
					new FileInputStream(arquivoNome), new CRC32());
			byte[] tempBuf = new byte[128];
			while (cis.read(tempBuf) >= 0) {
			}
			checksum = cis.getChecksum().getValue();
			cis.close();
		} catch (IOException e) {
			throw new ServicoException(log,
					"Erro de entrada/saída ao calcular o CRC32, "
							+ e.getMessage());
		}		
		return checksum;
	}

	/**
	 * Calcula o Checksum CRC32 de um array de bytes
	 * 
	 * @param bytes
	 *            o array de bytes
	 * @return o Checksum CRC32 do array de bytes
	 */
	public long calculaCRC32(byte[] bytes) throws ServicoException {
		// Computa CRC-32 checksum
		Checksum checksumEngine = new CRC32();
		checksumEngine.update(bytes, 0, bytes.length);
		return checksumEngine.getValue();
	}

	/**
	 * Calcula o Checksum CRC32 de uma string
	 * 
	 * @param str
	 *            a string
	 * @return o Checksum CRC32 da string
	 */
	public long calculaCRC32(String str) throws ServicoException {
		return calculaCRC32(str.getBytes());
	}

	public byte[] comprimirByteArray(byte[] input) {
		// Create the compressor with highest level of compression
		Deflater compressor = new Deflater();
		compressor.setLevel(Deflater.BEST_COMPRESSION);

		// Give the compressor the data to compress
		compressor.setInput(input);
		compressor.finish();

		// Create an expandable byte array to hold the compressed data.
		// You cannot use an array that's the same size as the orginal because
		// there is no guarantee that the compressed data will be smaller than
		// the uncompressed data.
		ByteArrayOutputStream bos = new ByteArrayOutputStream(input.length);

		// Compress the data
		byte[] buf = new byte[1024];
		while (!compressor.finished()) {
			int count = compressor.deflate(buf);
			bos.write(buf, 0, count);
		}
		try {
			bos.close();
		} catch (IOException e) {
		}

		// Get the compressed data
		byte[] compressedData = bos.toByteArray();

		return compressedData;
	}

	public byte[] descomprimirByteArray(byte[] compressedData) {
		// Create the decompressor and give it the data to compress
		Inflater decompressor = new Inflater();
		decompressor.setInput(compressedData);

		// Create an expandable byte array to hold the decompressed data
		ByteArrayOutputStream bos = new ByteArrayOutputStream(
				compressedData.length);

		// Decompress the data
		byte[] buf = new byte[1024];
		while (!decompressor.finished()) {
			try {
				int count = decompressor.inflate(buf);
				bos.write(buf, 0, count);
			} catch (DataFormatException e) {
			}
		}
		try {
			bos.close();
		} catch (IOException e) {
		}

		// Get the decompressed data
		byte[] decompressedData = bos.toByteArray();
		
		return decompressedData;
	}

}