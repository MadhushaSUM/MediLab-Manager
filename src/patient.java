

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class patient {
    private final int id;
    private final String name;

    private final LocalDate DOB;
    private final String stringDOB;
    private int ageY;
    private int ageM;
    private int ageD;
    boolean isYears,isMonths,isDays;

    private final String gender;
    private final int contactNumber;

    public patient(int id, String name, LocalDate DOB, String gender, int contactNumber) {
        this.id = id;
        this.name = name;
        this.DOB = DOB;
        this.stringDOB = DOB.toString();
        this.gender = gender;
        this.contactNumber = contactNumber;
        setAge(DOB);
    }
    private void setAge(LocalDate DOB) {
        try {
            Scanner fileScanner = new Scanner(new File("Configure\\AgeingPreferences.txt"));
            isYears = fileScanner.nextBoolean();
            isMonths = fileScanner.nextBoolean();
            isDays = fileScanner.nextBoolean();

            if (isYears && !isMonths && !isDays) {
                ageY = Period.between(DOB,LocalDate.now()).getYears();
                return;
            }
            if (isYears && isMonths && !isDays) {
                ageY = Period.between(DOB,LocalDate.now()).getYears();
                ageM = Period.between(DOB,LocalDate.now()).getMonths();
                return;
            }
            if (isYears && isMonths) {
                ageY = Period.between(DOB,LocalDate.now()).getYears();
                ageM = Period.between(DOB,LocalDate.now()).getMonths();
                ageD = Period.between(DOB,LocalDate.now()).getDays();
                return;
            }
            if (!isYears && isMonths && !isDays) {
                ageM = Integer.parseInt(String.valueOf(ChronoUnit.MONTHS.between(DOB,LocalDate.now())));
                return;
            }
            if (!isYears && isMonths) {
                ageM = Integer.parseInt(String.valueOf(ChronoUnit.MONTHS.between(DOB,LocalDate.now())));
                ageD = Period.between(DOB,LocalDate.now()).getDays();
                return;
            }
            if (!isYears && isDays) {
                ageD = Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(DOB,LocalDate.now())));
                return;
            }
            if (isYears) {
                ageY = Period.between(DOB,LocalDate.now()).getYears();
                ageD = Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(DOB,LocalDate.now().minusYears(ageY))));
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            messageBox.showMessage("AgeingPreferences.txt not found\nPlease restart the software","Error");
        }
    }

    public int getID() {return  id;}
    public String getName() {return name; }
    public LocalDate getDOB() {return DOB; }
    public int getAge() { return ageY; }
    public String getGender() {return gender; }
    public int getContactNumber() {return contactNumber; }
    public String getAgeToPrint() {
        if (isYears && !isMonths && !isDays) {
            if (ageY == 1) {
                return "01 Yr.";
            } else {
                return ageY + " Yrs.";
            }
        }
        if (isYears && isMonths && !isDays) {
            if (ageY == 1) {
                if (ageM == 1) {
                    return "01 Yr 01 month.";
                } else {
                    return "01 Yr "+ageM+" months.";
                }
            } else {
                if (ageM == 1) {
                    return ageY+" Yrs 01 month.";
                } else {
                    return ageY+" Yrs "+ageM+" months.";
                }
            }
        }
        if (isYears && isMonths) {
            if (ageY == 1) {
                if (ageM == 1) {
                    if (ageD == 1) {
                        return "01 Yr 01 month 01 day.";
                    } else {
                        return "01 Yr 01 month "+ageD+"days.";
                    }
                } else {
                    if (ageD == 1) {
                        return "01 Yr "+ageM+" months 01 day.";
                    } else {
                        return "01 Yr "+ageM+" months "+ageD+"days.";
                    }
                }
            } else {
                if (ageM == 1) {
                    if (ageD == 1) {
                        return ageY+" Yrs 01 month 01 day.";
                    } else {
                        return ageY+" Yrs 01 month "+ageD+"days.";
                    }
                } else {
                    if (ageD == 1) {
                        return ageY+" Yrs "+ageM+" months 01 day.";
                    } else {
                        return ageY+" Yrs "+ageM+" months "+ageD+"days.";
                    }
                }
            }
        }
        if (!isYears && isMonths && !isDays) {
            if (ageM == 1) {
                return "01 month.";
            } else {
                return ageM+" months.";
            }
        }
        if (!isYears && isMonths) {
            if (ageM == 1) {
                if (ageD == 1) {
                    return "01 month 01 day.";
                } else {
                    return "01 months "+ageD+" days.";
                }
            } else {
                if (ageD == 1) {
                    return ageM+" months 01 day.";
                } else {
                    return ageM+" months "+ageD+" days.";
                }
            }
        }
        if (!isYears && isDays) {
            if (ageD == 1) {
                return "01 day.";
            } else {
                return ageD+" days.";
            }
        }
        if (isYears) {
            if (ageY == 1) {
                if (ageD == 1) {
                    return "01 Yr 01 day.";
                } else {
                    return "01 Yr "+ageD+" days.";
                }
            } else {
                if (ageD == 1) {
                    return ageY+" Yrs 01 day.";
                } else {
                    return ageY+" Yrs "+ageD+" days.";
                }
            }
        }
        return "";
    }
    public String getStringDOB() { return stringDOB; }

    @Override
    public String toString() {
        return name +" ["+ ageY +"]";
    }
}
