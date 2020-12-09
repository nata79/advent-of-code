def has_sum(numbers, result)
  numbers.each_with_index do |x, index|
    numbers.slice(index + 1, numbers.size).each do |y|
      return true if x + y == result
    end
  end
  false
end

def find_weak_number(numbers, interval)
  numbers.each_with_index do |number, index|
    next if index < interval

    previous_numbers = numbers.slice(index - interval, interval)
    return number unless has_sum(previous_numbers, number)
  end
end

numbers = File.read('day9.txt').split("\n").map(&:to_i)

weak_number = find_weak_number(numbers, 25)

p weak_number

numbers.each_with_index do |x, i|
  next if x == weak_number
  acc = x
  set = [x]
  numbers.slice(i + 1, numbers.size).each do |y|
    if acc + y <= weak_number
      acc += y
      set << y
    else
      break
    end
  end
  if acc == weak_number
    p set.min + set.max
    break
  end
end