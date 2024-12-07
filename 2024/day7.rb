equations = []

File.read('day7.txt').split("\n").map do |line|
  result, opertors = line.split(': ')
  equations << [result.to_i, opertors.split(' ').map(&:to_i)]
end

def validate_equation(equation)
  result, operators = equation
  if operators.size == 1
    return result == operators[0]
  end

  if operators.size == 2
    return result == operators[0] + operators[1] || result == operators[0] * operators[1]
  end

  validate_equation([result, [operators[0] + operators[1], *operators[2..-1]]]) || 
  validate_equation([result, [operators[0] * operators[1], *operators[2..-1]]])
end

def validate_equation2(equation)
  result, operators = equation
  if operators.size == 1
    return result == operators[0]
  end

  if operators.size == 2
    return result == operators[0] + operators[1] || 
      result == operators[0] * operators[1] || 
      result == "#{operators[0]}#{operators[1]}".to_i
  end

  validate_equation2([result, [operators[0] + operators[1], *operators[2..-1]]]) || 
  validate_equation2([result, [operators[0] * operators[1], *operators[2..-1]]]) ||
  validate_equation2([result, ["#{operators[0]}#{operators[1]}".to_i, *operators[2..-1]]])
end

p equations.map { |equation| validate_equation(equation) ? equation[0] : 0 }.sum
p equations.map { |equation| validate_equation2(equation) ? equation[0] : 0 }.sum