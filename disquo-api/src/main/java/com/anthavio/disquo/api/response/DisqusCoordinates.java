package com.anthavio.disquo.api.response;

import java.io.Serializable;

/**
 * 
 * @author martin.vanek
 * 
 */
public class DisqusCoordinates implements Serializable {

	private static final long serialVersionUID = 1L;

	private Float lat;

	private Float lng;

	public Float getLat() {
		return this.lat;
	}

	public void setLat(Float lat) {
		this.lat = lat;
	}

	public Float getLng() {
		return this.lng;
	}

	public void setLng(Float lng) {
		this.lng = lng;
	}

	@Override
	public String toString() {
		return JsonStringBuilder.toString(this);
	}

}
