input = "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe
edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc
fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg
fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb
aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea
fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb
dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe
bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef
egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb
gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce"

# 0: abcefg
# 1: cf x
# 2: acdeg
# 3: acdfg x
# 4: bcdf x
# 5: abdfg
# 6: abdefg
# 7: acf x
# 8: abcdefg x
# 9: abcdfg x

p File.read('day8.txt').split("\n").map {  _1.split(' | ')[1].split(' ') }.flatten.count { [2,4,3,7].include?(_1.size) }

def find_five_and_six(patterns, nine)
  patterns.each do |pattern|
    if pattern.size == 5
      patterns.each do |pattern2|
        if pattern2.size == 6 && pattern2 != nine
          if pattern.chars.all? { pattern2.chars.include?(_1) }
            return [pattern, pattern2]
          end
        end
      end
    end
  end
end

def translate(dictionary, number)
  dictionary.each do |key, value|
    return value if number.size == key.size && number.chars.all? { key.include?(_1) }
  end
end

input2 = 'acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf'

results = File.read('day8.txt').split("\n").map do |note|
  patterns = note.split(' | ')[0].split(' ')

  one = patterns.find { _1.size == 2 }
  four = patterns.find { _1.size == 4 }
  seven = patterns.find { _1.size == 3 }
  eight = patterns.find { _1.size == 7 }

  a = (seven.chars - one.chars)[0]

  three = patterns.find { _1.include?(one.chars[0]) && _1.include?(one.chars[1]) && _1.include?(a) && _1.size == 5 }
  nine = patterns.find { |pattern| three.chars.all? { |x| pattern.include?(x)} && pattern.size == 6 }
  five, six = find_five_and_six(patterns, nine)
  zero = patterns.find { |x| x.size == 6 && x != nine && x != six }
  two = patterns.find { |x| x.size == 5 && x != three && x != five }

  # p "#{zero}: 0"
  # p "#{one}: 1"
  # p "#{two}: 2"
  # p "#{three}: 3"
  # p "#{four}: 4"
  # p "#{five}: 5"
  # p "#{six}: 6"
  # p "#{seven}: 7"
  # p "#{eight}: 8"
  # p "#{nine}: 9"

  dictionary = {
    zero => 0,
    one => 1,
    two => 2,
    three => 3,
    four => 4,
    five => 5,
    six => 6,
    seven => 7,
    eight => 8,
    nine => 9,
  }

  numbers = note.split(' | ')[1].split(' ')

  numbers = numbers.map do |number|
    translate(dictionary, number)
  end

  numbers.join('').to_i
end

p results.sum