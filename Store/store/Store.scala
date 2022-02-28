package store
import scala.collection.mutable.ListBuffer
import scala.sys.process._

object Store {
	
	class Stock {
		/* Products sold in the store */
		val tshirt = new Product(100, "Tshirt", 29.90f, 100)
		val sweatshirt = new Product(101, "Sweatshirt", 69.90f, 70)
		val cap = new Product(102, "Cap", 39.90f, 150)
		val sneaker = new Product(103, "Sneaker", 199.90f, 200)

		val itensStock = List(tshirt, sweatshirt, cap, sneaker)

		def imprime(): Unit = {
			for(i <- 0 until itensStock.length) {
				printf("ID:%d - %s - R$%.2f - In Stock: %d", itensStock(i).id, itensStock(i).name, itensStock(i).price, itensStock(i).amount)
				print("\n")
			}
		}

		def isInStock(id: Int): Boolean = {
			for(i <- 0 until itensStock.length) {
				if(itensStock(i).id == id)
					return true
			}
			return false
		}

		def returnItem(id: Int): Product = {
			var product: Product = itensStock(0)
			for(i <- 0 until itensStock.length) {
				if(itensStock(i).id == id) {
					var product: Product = itensStock(i)
					return product
				}
			}
			return product
		}
	}

	class Product(val id: Int, val name: String, var price: Float, var amount: Int) {
	
		def removeFromStock(qtd: Int): Unit = {
			amount -= qtd
		}
		
		def returnToStock(qtd: Int): Unit = {
			amount += qtd
		}
		
		override def toString = s"\nID: $id - Product: $name - Price: $price - Amount: $amount";
	}
		
	class ItemCart(var product: Product, val qtd: Int) {
		override def toString = s"Product: $product - Price: $product.price - Amount: $qtd";
	}
	
	class ShoppingCart {

		val itens = ListBuffer[ItemCart]()

		def addProduct(product: Product, qtd: Int): Unit = {
			if(qtd <= product.amount) {
				val itemCart = new ItemCart(product, qtd)
				itens += itemCart
				product.removeFromStock(qtd)
			}
			else 
				print(s"\nThere is not enough stock to ${product.name}. Current stock: ${product.amount}")
		}
		
 		def removeProduct(id: Int): Unit = {
 			for(i <- 0 until itens.length) {
 				if(itens(i).product.id == id) {
 					val product: Product = itens(i).product
 					product.returnToStock(itens(i).qtd)
 					itens -= itens(i)
 					return
 				}
 			}
 			print(s"\nID $id not found")	
 		}
	
		def purchaseSummary(): Unit = {
			print("LIST OF ITEMS IN CART: (item - price - amount)\n\n")
			for(item <- itens) {
				printf("%s - %.2f - %d", item.product.name, item.product.price, item.qtd)
				print("\n")
			}
		}
		
		def totalPurchase(): Unit = {
			var total: Float = 0
			for(item <- itens) { 
	    			total += item.product.price * item.qtd 
			}
			printf("TOTAL: %.2f", total)
		}
	}

	def clear() = "clear".!
}
