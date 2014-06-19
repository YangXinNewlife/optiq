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
 * Information for a call to {@link AggImplementor#implementResult(AggContext, AggResultContext)}
 * Typically, the aggregation implementation will convert {@link #accumulator()}
 * to the resulting value of the aggregation.
 * The implementation MUST NOT destroy the cotents of {@link #accumulator()}.
 * Note: logically, {@link WinAggResultContext} should extend {@link WinAggResetContext},
 * however this would prohibit usage of the same {@link AggImplementor} for both
 * regular aggregate and window aggregate.
 */
public abstract class WinAggResultContext
    extends AggResultContext
    implements WinAggImplementor.WinAggFrameContext,
      WinAggImplementor.WinAggFrameResultContext {

  /**
   * Creates window aggregate result context.
   * @param block code block that will contain the added initialization
   * @param accumulator accumulator variables that store the intermediate
   *                    aggregate state
   */
  public WinAggResultContext(BlockBuilder block,
      List<Expression> accumulator) {
    super(block, accumulator);
  }

  public final List<Expression> arguments(Expression rowIndex) {
    return rowTranslator(rowIndex).translateList(rexArguments());
  }
}

// End WinAggResultContext.java