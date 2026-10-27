package karatquestions_CITI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.Failure;
import org.junit.runner.Result;

class ReorderEntry {
	String sku;
	int warehouseId;
	int availableStock;
	int reorderQuantity;

	public ReorderEntry(String sku, int warehouseId, int availableStock, int reorderQuantity) {
		this.sku = sku;
		this.warehouseId = warehouseId;
		this.availableStock = availableStock;
		this.reorderQuantity = reorderQuantity;
	}
}

class TransferEntry {
	String sku;
	int sourceWarehouseId;
	int destinationWarehouseId;
	int quantity;

	public TransferEntry(String sku, int sourceWarehouseId, int destinationWarehouseId, int quantity) {
		this.sku = sku;
		this.sourceWarehouseId = sourceWarehouseId;
		this.destinationWarehouseId = destinationWarehouseId;
		this.quantity = quantity;
	}
}

class ShortageEntry {
	String sku;
	int destinationWarehouseId;
	int remainingQuantity;

	public ShortageEntry(String sku, int destinationWarehouseId, int remainingQuantity) {
		this.sku = sku;
		this.destinationWarehouseId = destinationWarehouseId;
		this.remainingQuantity = remainingQuantity;
	}
}

class TransferPlanResult {
	List<TransferEntry> transfers = new ArrayList<>();
	List<ShortageEntry> unfulfilledShortages = new ArrayList<>();
}

class InventoryRecord {
	String sku;
	int warehouseId;
	int onHandQuantity;
	int reservedQuantity;
	int updatedAt;

	public InventoryRecord(String sku, int warehouseId, int onHandQuantity, int reservedQuantity, int updatedAt) {
		this.sku = sku;
		this.warehouseId = warehouseId;
		this.onHandQuantity = onHandQuantity;
		this.reservedQuantity = reservedQuantity;
		this.updatedAt = updatedAt;
	}
}

class InventoryUpdate {
	String sku;
	int warehouseId;
	int onHandQuantity;
	int reservedQuantity;
	int updatedAt;

	public InventoryUpdate(String sku, int warehouseId, int onHandQuantity, int reservedQuantity, int updatedAt) {
		this.sku = sku;
		this.warehouseId = warehouseId;
		this.onHandQuantity = onHandQuantity;
		this.reservedQuantity = reservedQuantity;
		this.updatedAt = updatedAt;
	}
}

class StockLedger {
	Map<String, InventoryRecord> inventoryRecords = new HashMap<>();
	List<InventoryUpdate> inventoryUpdates = new ArrayList<>();

	public String recordKey(String sku, int warehouseId) {
		return sku + ":" + warehouseId;
	}

	public void registerInventoryRecord(InventoryRecord record) {
		inventoryRecords.put(recordKey(record.sku, record.warehouseId), record);
	}

	public void applyInventoryUpdate(InventoryUpdate update) {
		String key = recordKey(update.sku, update.warehouseId);

		if (inventoryRecords.get(key) == null) {
			return;
		}

		InventoryRecord record = inventoryRecords.get(key);
// Bug is likely to be here in following condition.
// before : if(record.updatedAt < update.updatedAt) 
		if (record.updatedAt <= update.updatedAt) {
			record.onHandQuantity = update.onHandQuantity;
			record.reservedQuantity = update.reservedQuantity;
			record.updatedAt = update.updatedAt;
		}
	}

	public int getAvailableStock(String sku, int warehouseId) {
		String key = recordKey(sku, warehouseId);

		if (!inventoryRecords.containsKey(key)) {
			return 0;
		}

		InventoryRecord record = inventoryRecords.get(key);

		return Math.max(record.onHandQuantity - record.reservedQuantity, 0);
	}
	
	public Integer previewUpdate(InventoryUpdate update) {
		String key = recordKey(update.sku, update.warehouseId);
		
		InventoryRecord record = inventoryRecords.get(key);
		
		if(record == null) {
			return null;
		}
		
		return update.onHandQuantity - record.onHandQuantity;
	}
	
	public Map<String, Integer> getAvailableStockBySku(){
		Map<String, Integer> availableStockBySKU = new HashMap<>();
		
		for(InventoryRecord record : inventoryRecords.values()) {
			int availableStock = Math.max(record.onHandQuantity-record.reservedQuantity,0);
			
			int currentTotal = availableStockBySKU.getOrDefault(record.sku, 0);
			
			availableStockBySKU.put(record.sku, currentTotal+availableStock);
			
		}
		
		return availableStockBySKU;
	}
}

public class Solution5_StockLedgerINWarehouse {

	public static class TestSuite {

		@Test
		public void testApplyInventoryUpdate1() {
			// Test that a newer update's values replace the record's stored values,
			// including a boundary update whose timestamp equals the record's current
			// timestamp.
			System.out.println("Running testApplyInventoryUpdate1");

			StockLedger ledger = new StockLedger();
			ledger.registerInventoryRecord(new InventoryRecord("SKU-1", 101, 100, 20, 3000));

			ledger.applyInventoryUpdate(new InventoryUpdate("SKU-1", 101, 80, 10, 2000));

			InventoryRecord record = ledger.inventoryRecords.get("SKU-1:101");

			assertEquals(100, record.onHandQuantity);
			System.out.println("record.onHandQuantity" + record.onHandQuantity);
			assertEquals(20, record.reservedQuantity);
			assertEquals(3000, record.updatedAt);
			assertEquals(80, ledger.getAvailableStock("SKU-1", 101));

			ledger.applyInventoryUpdate(new InventoryUpdate("SKU-1", 101, 120, 30, 4000));

			assertEquals(120, record.onHandQuantity);
			assertEquals(30, record.reservedQuantity);
			assertEquals(4000, record.updatedAt);
			assertEquals(90, ledger.getAvailableStock("SKU-1", 101));

			// Update timestamp equal to the record's current timestamp (boundary).
			ledger.applyInventoryUpdate(new InventoryUpdate("SKU-1", 101, 200, 50, 4000));

			assertEquals(200, record.onHandQuantity);
			assertEquals(50, record.reservedQuantity);
			assertEquals(4000, record.updatedAt);
		}

		@Test
		public void testApplyInventoryUpdate2() {
			// Test that an update for an unknown SKU and warehouse pair is ignored.
			System.out.println("Running testApplyInventoryUpdate2");

			StockLedger ledger = new StockLedger();
			ledger.registerInventoryRecord(new InventoryRecord("SKU-1", 101, 100, 20, 3000));

			ledger.applyInventoryUpdate(new InventoryUpdate("SKU-2", 202, 50, 5, 4000));

			assertEquals(1, ledger.inventoryRecords.size());
			assertFalse(ledger.inventoryRecords.containsKey("SKU-2:202"));
		}

		@Test
		public void testApplyInventoryUpdate3() {
			// Test that available stock is floored at 0 when an applied update leaves
			// reserved quantity greater than on-hand quantity.
			System.out.println("Running testApplyInventoryUpdate3");

			StockLedger ledger = new StockLedger();
			ledger.registerInventoryRecord(new InventoryRecord("SKU-1", 101, 100, 20, 1000));

			ledger.applyInventoryUpdate(new InventoryUpdate("SKU-1", 101, 30, 50, 2000));

			InventoryRecord record = ledger.inventoryRecords.get("SKU-1:101");

			assertEquals(30, record.onHandQuantity);
			// System.out.println("record.onHandQuantity"+record.onHandQuantity);
			assertEquals(50, record.reservedQuantity);
			// System.out.println("record.reservedQuantity"+record.reservedQuantity);
			assertEquals(0, ledger.getAvailableStock("SKU-1", 101));
			// System.out.println("ledger.getAvailableStock"+ledger.getAvailableStock("SKU-1",
			// 101));
		}
		
		 @Test public void testPreviewUpdate1() { // Test that the impact reflects the
//		 on-hand quantity change without modifying the record, in both directions.
		 System.out.println("Running testPreviewUpdate1");
		  
		 StockLedger ledger = new StockLedger(); ledger.registerInventoryRecord( new
		 InventoryRecord("SKU-1", 101, 100, 20, 3000) );
		
		 Integer impact = ledger.previewUpdate( new InventoryUpdate("SKU-1", 101, 80,
		 10, 4000) ); assertEquals(Integer.valueOf(-20), impact);
		
		 impact = ledger.previewUpdate( new InventoryUpdate("SKU-1", 101, 150, 30,
		 1000) ); assertEquals(Integer.valueOf(50), impact);
		
		 InventoryRecord record = ledger.inventoryRecords.get("SKU-1:101");
		 
		 assertEquals(100, record.onHandQuantity); assertEquals(3000,
		 record.updatedAt); }
		  
		 @Test public void testPreviewUpdate2() { // Test that a zero-impact update
//		 returns 0, and that an unknown SKU and warehouse pair returns null.
		 System.out.println("Running testPreviewUpdate2");
		  
		 StockLedger ledger = new StockLedger(); ledger.registerInventoryRecord( new
		 InventoryRecord("SKU-1", 101, 100, 20, 1000) );
		 
		 Integer impact = ledger.previewUpdate( new InventoryUpdate("SKU-1", 101, 100,
		 30, 2000) ); assertEquals(Integer.valueOf(0), impact);
		 
		 impact = ledger.previewUpdate( new InventoryUpdate("SKU-2", 202, 50, 5, 9000)
		 ); assertEquals(null, impact); }
		 
		
		 @Test public void testGetAvailableStockBySku1() { // Test that available
//		 * stock is summed across all warehouses for the same SKU, grouped by SKU.
		  System.out.println("Running testGetAvailableStockBySku1");
		  
		 StockLedger ledger = new StockLedger(); ledger.registerInventoryRecord( new
		 InventoryRecord("SKU-1", 101, 100, 20, 1000) );
		 ledger.registerInventoryRecord( new InventoryRecord("SKU-1", 102, 50, 10,
		 1000) ); ledger.registerInventoryRecord( new InventoryRecord("SKU-2", 101,
		 30, 5, 1000) );
		 
		 Map<String, Integer> result = ledger.getAvailableStockBySku();
		
		 Map<String, Integer> expected = new HashMap<>(); expected.put("SKU-1", 120);
		 expected.put("SKU-2", 25);
		
		 assertEquals(expected, result); }
		 

		
		  @Test public void testGetAvailableStockBySku2() { // Test that a record with
//		 reserved quantity exceeding on-hand quantity contributes zero, not a negative
//		  amount. 
		  System.out.println("Running testGetAvailableStockBySku2");
		 
		  StockLedger ledger = new StockLedger(); ledger.registerInventoryRecord( new
		  InventoryRecord("SKU-1", 101, 100, 20, 1000) );
		  ledger.registerInventoryRecord( new InventoryRecord("SKU-1", 102, 10, 50,
		  1000) );
		  
		  Map<String, Integer> result = ledger.getAvailableStockBySku();
		  
		  Map<String, Integer> expected = new HashMap<>(); expected.put("SKU-1", 80);
		  
		  assertEquals(expected, result); }
		 
		
		 @Test public void testGetAvailableStockBySku3() { 
			 // Test that an empty
//		 ledger returns an empty result, and that a SKU whose only record has zero
//		 available stock still appears with a value of 0.
		 System.out.println("Running testGetAvailableStockBySku3");
		 
		 StockLedger ledger = new StockLedger();
		  
		  assertEquals(new HashMap<>(), ledger.getAvailableStockBySku());
		  
		  ledger.registerInventoryRecord( new InventoryRecord("SKU-1", 101, 10, 50,
		  1000) );
		  
		  Map<String, Integer> result = ledger.getAvailableStockBySku();
		  
		  Map<String, Integer> expected = new HashMap<>(); expected.put("SKU-1", 0);
		 
		  assertEquals(expected, result); }
		 
	}

	public static void main(String[] args) {
		Result result = JUnitCore.runClasses(TestSuite.class);

		for (Failure failure : result.getFailures()) {
			System.out.println(failure.getTrace());
		}

		if (result.wasSuccessful()) {
			System.out.println("All tests passed.");
		} else {
			System.out.println("Some tests failed.");
		}
	}

}
