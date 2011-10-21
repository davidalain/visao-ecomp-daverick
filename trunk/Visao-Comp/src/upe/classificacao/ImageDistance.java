package upe.classificacao;

public class ImageDistance {

	private ImageClass imgClass;
	private double distance;
	public ImageDistance(ImageClass imgClass, double distance) {
		super();
		this.imgClass = imgClass;
		this.distance = distance;
	}
	public ImageClass getImgClass() {
		return imgClass;
	}
	public void setImgClass(ImageClass imgClass) {
		this.imgClass = imgClass;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
}
