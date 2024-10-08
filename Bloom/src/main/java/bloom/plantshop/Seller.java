package bloom.plantshop;

class Seller extends User {
	private String storeName;

	public Seller(String username, String email, String password, String type, String storeName) {
		super(username, email, password, type);
		this.setStoreName(storeName);

	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	@Override
	public String toString() {

		String sellerData = (super.toString() + "\nStore name: " + storeName);

		return sellerData;
	}

}
