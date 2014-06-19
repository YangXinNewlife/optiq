/*
// Licensed to Julian Hyde under one or more contributor license
// agreements. See the NOTICE file distributed with this work for
// additional information regarding copyright ownership.
//
// Julian Hyde licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except in
// compliance with the License. You may obtain a copy of the License at:
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
*/
package net.hydromatic.optiq.rules.java;

import net.hydromatic.linq4j.expressions.BlockBuilder;
import net.hydromatic.linq4j.expressions.Expression;

import java.util.List;

/**
 * Information for a call to {@link AggImplementor#implementReset(AggContext, AggResetContext)}.
 * {@link AggResetContext} provides access to the accumulator variables
 * that should be reset.
 * Note: the very first reset of windowed aggregates is performed with null
 * knowledge of indices and row count in the partition.
 * In other words, the implementation should treat indices and partition row
 * count as a hint to pre-size the collections.
 */
public class WinAggResetContext extends AggResetContext
    implements WinAggImplementor.WinAggFrameContext {
  private final Expression index;
  private final Expression startIndex;
  private final Expression endIndex;
  private final Expression partitionRowCount;
  private final Expression hasRows;

  /**
   * Creates window aggregate reset context.
   * @param block code block that will contain the added initialization
   * @param accumulator accumulator variables that store the intermediate
   *                    aggregate state
   * @param index index of the current row in the partition
   * @param startIndex index of the very first row in partition
   * @param endIndex index of the very last row in partition
   * @param hasRows boolean expression that tells if the partition has rows
   * @param partitionRowCount number of rows in the current partition or
   *                          0 if the partition is empty
   *
   */
  public WinAggResetContext(BlockBuilder block,
      List<Expression> accumulator, Expression index,
      Expression startIndex, Expression endIndex,
      Expression hasRows, Expression partitionRowCount) {
    super(block, accumulator);
    this.index = index;
    this.startIndex = startIndex;
    this.endIndex = endIndex;
    this.partitionRowCount = partitionRowCount;
    this.hasRows = hasRows;
  }

  public Expression index() {
    return index;
  }

  public Expression startIndex() {
    return startIndex;
  }

  public Expression endIndex() {
    return endIndex;
  }

  public Expression hasRows() {
    return hasRows;
  }

  public Expression getPartitionRowCount() {
    return partitionRowCount;
  }
}
