import store.Store._

object Main {

	def main(args: Array[String]): Unit = {
		var finishPurchase: Boolean = true

		val stock = new Stock
		val newClient = new ShoppingCart

		while(finishPurchase) {
			clear()
			print("======= PRODUCTS AVAILABLE =======\n")
			stock.imprime()
			print("-------------------------------------------------\n")
			print("Type the product ID to add it to shopping cart\n")
			print("Type 1 to remove an item from shopping cart\n")
			print("Type 2 to list items in shopping cart\n")
			print("Type 0 to finish purchase\n\n")
			print("Enter your choice:\t")
			var id: Int = scala.io.StdIn.readInt()

			id match {
				case 1 => 
					print("\nEnter the product ID:\t")
					id=scala.io.StdIn.readInt()
					newClient.removeProduct(id)
					print("\n\nProduct removed from cart successfully. Press Enter to continue ...")
					System.in.read
				case 2 =>
					clear()
					newClient.purchaseSummary()
					print("\n\nPress Enter to continue ...")
					System.in.read
				case 0 =>
					clear()
					finishPurchase = false
					newClient.purchaseSummary()
					newClient.totalPurchase()
					print("\n\n\n")
				case _ =>
					var item: Boolean = stock.isInStock(id)
					if(item) {
						print("Enter the amount wanted:\t")
						var amount: Int = scala.io.StdIn.readInt()
						var product: Product = stock.returnItem(id)
						newClient.addProduct(product, amount)
						print("\n\nProduct added to cart successfully. Press Enter to continue ...")
						System.in.read
					}
					else print("ERROR: product not found in stock")
			}
		}
	}
}
