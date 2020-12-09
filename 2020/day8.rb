instructions = File.read('day8.txt')
                   .split("\n")
                   .map do |instruction|
                     { operation: instruction.split(' ')[0], argument: instruction.split(' ')[1].to_i, executions: 0 }
                   end

def run(instructions, max_executions)
  accumulator = 0
  position = 0

  while position < instructions.size
    instruction = instructions[position]

    break if instruction[:executions] > max_executions

    case instruction[:operation]
    when 'nop'
      position += 1
    when 'acc'
      accumulator += instruction[:argument]
      position += 1
    when 'jmp'
      position += instruction[:argument]
    end

    instruction[:executions] += 1
  end

  [accumulator, position]
end

instructions.each_with_index do |instruction, index|
  fixed_instructions = instructions.dup.map(&:dup)

  if instruction[:operation] == 'nop'
    fixed_instructions[index] = { operation: 'jmp', argument: instruction[:argument], executions: 0 }
  elsif instruction[:operation] == 'jmp'
    fixed_instructions[index] = { operation: 'nop', argument: instruction[:argument], executions: 0 }
  end

  accumulator, position = run(fixed_instructions, 1)

  if position == fixed_instructions.size
    p accumulator
    break
  end
end
