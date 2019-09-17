/**
 * 
 */
package it.cambi.celum.mongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author luca
 *
 */
@Document(collection = "database_sequences")
public class DataBaseSequence {

	@Id
	private String id;

	private long seq;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

}
