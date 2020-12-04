def height_valid(height)
  height.end_with?('cm') ? (150...194).include?(height.to_i) : (59...77).include?(height.to_i)
end

passports = File.read('day4.txt').split("\n\n")
                .map { |passport| passport.split(/ |\n/) }
                .map { |passport| passport.reduce({}) { |acc, attr| acc.tap { acc[attr.split(':')[0]] = attr.split(':')[1] } } }

valid_passports = passports.filter do |passport|
  passport.include?('byr') &&
    (1920...2003).include?(passport['byr'].to_i) &&
    passport.include?('iyr') &&
    (2010...2021).include?(passport['iyr'].to_i) &&
    passport.include?('eyr') &&
    (2020...2031).include?(passport['eyr'].to_i) &&
    passport.include?('hgt') &&
    height_valid(passport['hgt']) &&
    passport.include?('hcl') &&
    passport['hcl'].match?(/\A#[0-9a-f]{6}\z/) &&
    passport.include?('ecl') &&
    %w[amb blu brn gry grn hzl oth].include?(passport['ecl']) &&
    passport.include?('pid') &&
    passport['pid'].match?(/\A\d{9}\z/)
end.size

p valid_passports
