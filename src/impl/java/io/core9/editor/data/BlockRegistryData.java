/**
 *  Copyright 2012 Wordnik, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package io.core9.editor.data;

import io.core9.editor.model.BlockRegistryItem;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class BlockRegistryData {
	static List<BlockRegistryItem> blockRegistryItems = new ArrayList<BlockRegistryItem>();

	static {
		blockRegistryItems.add(createBlockRegistryItem(1, 1, 2, new Date(), "placed"));
		blockRegistryItems.add(createBlockRegistryItem(2, 1, 2, new Date(), "delivered"));
		blockRegistryItems.add(createBlockRegistryItem(3, 2, 2, new Date(), "placed"));
		blockRegistryItems.add(createBlockRegistryItem(4, 2, 2, new Date(), "delivered"));
		blockRegistryItems.add(createBlockRegistryItem(5, 3, 2, new Date(), "placed"));
		blockRegistryItems.add(createBlockRegistryItem(11, 3, 2, new Date(), "placed"));
		blockRegistryItems.add(createBlockRegistryItem(12, 3, 2, new Date(), "placed"));
		blockRegistryItems.add(createBlockRegistryItem(13, 3, 2, new Date(), "placed"));
		blockRegistryItems.add(createBlockRegistryItem(14, 3, 2, new Date(), "placed"));
		blockRegistryItems.add(createBlockRegistryItem(15, 3, 2, new Date(), "placed"));
	}

	public BlockRegistryItem findBlockRegistryItemById(long orderId) {
		for (BlockRegistryItem order : blockRegistryItems) {
			if (order.getId() == orderId) {
				return order;
			}
		}
		return null;
	}

	public void placeBlockRegistryItem(BlockRegistryItem order) {
		if (blockRegistryItems.size() > 0) {
			for (int i = blockRegistryItems.size() - 1; i >= 0; i--) {
				if (blockRegistryItems.get(i).getId() == order.getId()) {
					blockRegistryItems.remove(i);
				}
			}
		}
		blockRegistryItems.add(order);
	}

	public void deleteBlockRegistryItem(long orderId) {
		if (blockRegistryItems.size() > 0) {
			for (int i = blockRegistryItems.size() - 1; i >= 0; i--) {
				if (blockRegistryItems.get(i).getId() == orderId) {
					blockRegistryItems.remove(i);
				}
			}
		}
	}

	private static BlockRegistryItem createBlockRegistryItem(long id, long petId, int quantity,
			Date shipDate, String status) {
		BlockRegistryItem order = new BlockRegistryItem();
		order.setId(id);
		order.setPetId(petId);
		order.setQuantity(quantity);
		order.setShipDate(shipDate);
		order.setStatus(status);
		return order;
	}
}